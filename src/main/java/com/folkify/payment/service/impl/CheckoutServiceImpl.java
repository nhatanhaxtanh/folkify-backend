package com.folkify.payment.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.User;
import com.folkify.common.exception.ApiException;
import com.folkify.common.exception.ErrorCode;
import com.folkify.payment.config.Pay2sProperties;
import com.folkify.payment.dto.request.CheckoutRequest;
import com.folkify.payment.dto.request.Pay2sCreateLinkRequest;
import com.folkify.payment.dto.response.CheckoutResponse;
import com.folkify.payment.dto.response.PaymentStatusResponse;
import com.folkify.payment.entity.PaymentTransaction;
import com.folkify.payment.enumType.TransactionStatus;
import com.folkify.payment.repository.PaymentTransactionRepository;
import com.folkify.payment.service.CheckoutService;
import com.folkify.payment.util.Pay2sSignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private static final Logger log = LoggerFactory.getLogger(CheckoutServiceImpl.class);
    private static final String REQUEST_TYPE = "pay2s";

    private final PaymentTransactionRepository transactionRepo;
    private final RestTemplate pay2sRestTemplate;
    private final Pay2sProperties props;
    private final ObjectMapper objectMapper;

    public CheckoutServiceImpl(PaymentTransactionRepository transactionRepo,
                               RestTemplate pay2sRestTemplate,
                               Pay2sProperties props,
                               ObjectMapper objectMapper) {
        this.transactionRepo = transactionRepo;
        this.pay2sRestTemplate = pay2sRestTemplate;
        this.props = props;
        this.objectMapper = objectMapper;
    }

    @Override
    public CheckoutResponse createCheckout(User currentUser, CheckoutRequest request) {
        Plan targetPlan = request.plan();

        if (targetPlan == null || targetPlan == Plan.FREE) {
            throw new ApiException(ErrorCode.INVALID_PLAN, "Gói không hợp lệ để thanh toán");
        }

        Long price = props.getPlanPrices().get(targetPlan);
        if (price == null || price <= 0) {
            throw new ApiException(ErrorCode.INVALID_PLAN,
                    "Chưa cấu hình giá cho gói " + targetPlan);
        }

        if (currentUser.getPlan() == targetPlan) {
            throw new ApiException(ErrorCode.ALREADY_ON_PLAN, "Bạn đang sử dụng gói này rồi");
        }

        // Nội dung CK phải là chữ + số, ngắn gọn để in vừa vào bản tin ngân hàng.
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        String transferContent = "FOLKIFY" + targetPlan.name() + suffix;
        String orderId = UUID.randomUUID().toString().replace("-", "").substring(0, 15);

        // Lưu giao dịch PENDING và commit ngay (method không @Transactional) trước khi gọi Pay2S.
        PaymentTransaction txn = new PaymentTransaction();
        txn.setUser(currentUser);
        txn.setTargetPlan(targetPlan);
        txn.setGatewayReferenceId(orderId);
        txn.setAmount(BigDecimal.valueOf(price));
        txn.setTransferContent(transferContent);
        txn.setTransactionDate(LocalDateTime.now());
        txn.setStatus(TransactionStatus.PENDING);
        transactionRepo.saveAndFlush(txn);

        return callPay2sWithRetry(orderId, transferContent, price);
    }

    /** Tạo payload Pay2S với requestId + chữ ký mới cho mỗi lần thử. */
    private Pay2sCreateLinkRequest buildPayload(String orderId, String transferContent, long price) {
        String amountStr = String.valueOf(price);
        // requestId phải duy nhất từng lần gọi (tránh cổng coi là trùng và trả về rỗng).
        String requestId = System.currentTimeMillis() + String.format("%03d", (int) (Math.random() * 1000));

        String signature = Pay2sSignatureUtil.generateSignature(
                props.getAccessKey(), amountStr, props.getIpnUrl(), orderId, transferContent,
                props.getPartnerCode(), props.getRedirectUrl(), requestId, REQUEST_TYPE,
                props.getSecretKey());

        return new Pay2sCreateLinkRequest(
                props.getAccessKey(),
                props.getPartnerCode(),
                props.getPartnerName(),
                requestId,
                price,
                orderId,
                transferContent,
                REQUEST_TYPE,
                List.of(new Pay2sCreateLinkRequest.BankAccountPayload(
                        props.getBank().getAccountNumber(), props.getBank().getBankId())),
                props.getRedirectUrl(),
                props.getIpnUrl(),
                REQUEST_TYPE,
                signature);
    }

    @Override
    public PaymentStatusResponse getStatus(User currentUser, String orderId) {
        PaymentTransaction txn = transactionRepo.findByGatewayReferenceId(orderId)
                .orElseThrow(() -> new ApiException(ErrorCode.PAYMENT_NOT_FOUND));

        if (txn.getUser() == null || !txn.getUser().getId().equals(currentUser.getId())) {
            throw new ApiException(ErrorCode.FORBIDDEN);
        }

        return new PaymentStatusResponse(orderId, txn.getStatus(), txn.getTargetPlan());
    }

    private CheckoutResponse callPay2sWithRetry(String orderId, String transferContent, long price) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

        final int maxAttempts = 3;
        Exception lastException = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                // Payload mới (requestId + chữ ký mới) cho mỗi lần thử.
                Pay2sCreateLinkRequest payload = buildPayload(orderId, transferContent, price);
                HttpEntity<Pay2sCreateLinkRequest> entity = new HttpEntity<>(payload, headers);

                log.info("Gọi Pay2S lần {} | OrderId: {} | requestId: {}",
                        attempt, orderId, payload.requestId());
                ResponseEntity<String> response =
                        pay2sRestTemplate.postForEntity(props.getApiUrl(), entity, String.class);
                String body = response.getBody();
                log.info("Pay2S response | status={} | body={}", response.getStatusCode(), body);

                if (body != null && !body.isBlank()) {
                    JsonNode node = objectMapper.readTree(body);
                    if (node.hasNonNull("payUrl")) {
                        return new CheckoutResponse(node.get("payUrl").asText(), orderId);
                    }
                    log.error("Pay2S không trả về payUrl! Response: {}", body);
                } else {
                    log.warn("Pay2S trả về body rỗng (status {})", response.getStatusCode());
                }
            } catch (org.springframework.web.client.RestClientResponseException e) {
                lastException = e;
                log.warn("Pay2S attempt {} lỗi HTTP {} | body: {}",
                        attempt, e.getStatusCode(), e.getResponseBodyAsString());
            } catch (Exception e) {
                lastException = e;
                log.warn("Pay2S attempt {} thất bại: {}", attempt, e.toString());
            }

            // Backoff tăng dần trước khi thử lại (cho cổng thời gian hồi phục).
            if (attempt < maxAttempts) {
                try {
                    Thread.sleep(600L * attempt);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        log.error("Pay2S thất bại sau 2 lần thử", lastException);
        throw new ApiException(ErrorCode.PAYMENT_GATEWAY_ERROR, "Lỗi kết nối cổng thanh toán");
    }
}
