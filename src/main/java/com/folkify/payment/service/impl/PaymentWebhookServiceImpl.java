package com.folkify.payment.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.User;
import com.folkify.auth.repository.UserRepository;
import com.folkify.payment.config.PayOsProperties;
import com.folkify.payment.entity.PaymentTransaction;
import com.folkify.payment.entity.PaymentWebhookLog;
import com.folkify.payment.enumType.ProcessingStatus;
import com.folkify.payment.enumType.TransactionStatus;
import com.folkify.payment.repository.PaymentTransactionRepository;
import com.folkify.payment.repository.PaymentWebhookLogRepository;
import com.folkify.payment.service.PaymentWebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.model.webhooks.WebhookData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    private static final Logger log = LoggerFactory.getLogger(PaymentWebhookServiceImpl.class);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /** Mã "00" của PayOS nghĩa là giao dịch thành công. */
    private static final String SUCCESS_CODE = "00";

    private final PaymentWebhookLogRepository webhookLogRepository;
    private final PaymentTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PayOS payOS;
    private final PayOsProperties props;

    public PaymentWebhookServiceImpl(PaymentWebhookLogRepository webhookLogRepository,
                                     PaymentTransactionRepository transactionRepository,
                                     UserRepository userRepository,
                                     ObjectMapper objectMapper,
                                     PayOS payOS,
                                     PayOsProperties props) {
        this.webhookLogRepository = webhookLogRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.payOS = payOS;
        this.props = props;
    }

    @Override
    @Transactional
    public void processPayosWebhook(Object body, String clientIp) {
        // 1. Luôn lưu lại raw payload để đối soát / debug về sau.
        PaymentWebhookLog webhookLog = new PaymentWebhookLog();
        webhookLog.setGateway("PAYOS");
        webhookLog.setRawPayload(serialize(body));
        webhookLog.setClientIp(clientIp);
        webhookLog.setProcessingStatus(ProcessingStatus.PENDING);
        webhookLog = webhookLogRepository.saveAndFlush(webhookLog);

        // 2. Xác thực chữ ký bằng checksum key (SDK tự lo). Sai chữ ký -> ném exception.
        WebhookData data;
        try {
            data = payOS.webhooks().verify(body);
        } catch (Exception e) {
            log.warn("Webhook PayOS sai chữ ký hoặc payload lỗi | IP: {}", clientIp, e);
            updateLogStatus(webhookLog, ProcessingStatus.IGNORED, "Invalid signature");
            return;
        }

        try {
            if (!SUCCESS_CODE.equals(data.getCode())) {
                updateLogStatus(webhookLog, ProcessingStatus.IGNORED,
                        "Non-success code: " + data.getCode());
                return;
            }

            if (data.getOrderCode() == null) {
                updateLogStatus(webhookLog, ProcessingStatus.IGNORED, "Missing orderCode");
                return;
            }

            boolean handled = processTransaction(data);
            updateLogStatus(webhookLog,
                    handled ? ProcessingStatus.PROCESSED : ProcessingStatus.IGNORED,
                    handled ? null : "No matching transaction");
        } catch (Exception e) {
            log.error("Lỗi xử lý webhook PayOS", e);
            updateLogStatus(webhookLog, ProcessingStatus.FAILED, e.getMessage());
        }
    }

    /** @return true nếu khớp và xử lý một giao dịch chờ; false nếu không tìm thấy (VD: webhook test). */
    private boolean processTransaction(WebhookData data) {
        String orderCode = String.valueOf(data.getOrderCode());

        // Khóa dòng để chặn 2 webhook trùng xử lý song song cùng một orderCode.
        PaymentTransaction target =
                transactionRepository.findByGatewayReferenceIdForUpdate(orderCode).orElse(null);

        if (target == null) {
            log.info("Không tìm thấy giao dịch chờ khớp cho orderCode: {}", orderCode);
            return false;
        }

        if (target.getStatus() == TransactionStatus.SUCCESS) {
            log.warn("Giao dịch [{}] đã xử lý trước đó. Bỏ qua webhook trùng.", orderCode);
            return true;
        }

        target.setBankTransactionCode(data.getReference());
        if (data.getAmount() != null) {
            target.setAmount(BigDecimal.valueOf(data.getAmount()));
        }
        target.setTransactionDate(parseDate(data.getTransactionDateTime()));
        target.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.saveAndFlush(target);

        // Nâng gói + tính hạn cho user.
        User user = target.getUser();
        Plan newPlan = target.getTargetPlan();
        if (user != null && newPlan != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime currentExpiry = user.getPlanExpiresAt();
            // Mua đúng gói đang còn hạn -> gia hạn cộng dồn; ngược lại tính từ hôm nay.
            LocalDateTime base = (user.getPlan() == newPlan
                    && currentExpiry != null && currentExpiry.isAfter(now))
                    ? currentExpiry : now;
            LocalDateTime newExpiry = base.plusDays(props.getPlanDurationDays());

            user.setPlan(newPlan);
            user.setPlanExpiresAt(newExpiry);
            userRepository.save(user);
            log.info("Đã kích hoạt gói {} cho user [{}] đến {} | orderCode: {}",
                    newPlan, user.getId(), newExpiry, orderCode);
        } else {
            log.error("Giao dịch [{}] thiếu user/targetPlan, không thể nâng gói.", orderCode);
        }
        return true;
    }

    private LocalDateTime parseDate(String raw) {
        if (raw == null || raw.isBlank()) {
            return LocalDateTime.now();
        }
        try {
            return LocalDateTime.parse(raw, DATE_FMT);
        } catch (Exception ignored) {
            return LocalDateTime.now();
        }
    }

    private String serialize(Object body) {
        try {
            return objectMapper.writeValueAsString(body);
        } catch (Exception e) {
            return String.valueOf(body);
        }
    }

    private void updateLogStatus(PaymentWebhookLog logRecord, ProcessingStatus status, String errorMsg) {
        logRecord.setProcessingStatus(status);
        logRecord.setErrorMessage(errorMsg);
        webhookLogRepository.save(logRecord);
    }
}
