package com.folkify.payment.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkify.auth.entity.User;
import com.folkify.auth.repository.UserRepository;
import com.folkify.payment.config.Pay2sProperties;
import com.folkify.payment.dto.Pay2sTransaction;
import com.folkify.payment.dto.Pay2sWebhookRequest;
import com.folkify.payment.entity.PaymentTransaction;
import com.folkify.payment.entity.PaymentWebhookLog;
import com.folkify.payment.enumType.ProcessingStatus;
import com.folkify.payment.enumType.TransactionStatus;
import com.folkify.payment.repository.PaymentTransactionRepository;
import com.folkify.payment.repository.PaymentWebhookLogRepository;
import com.folkify.payment.service.PaymentWebhookService;
import com.folkify.payment.util.Pay2sSignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    private static final Logger log = LoggerFactory.getLogger(PaymentWebhookServiceImpl.class);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final PaymentWebhookLogRepository webhookLogRepository;
    private final PaymentTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final Pay2sProperties props;

    public PaymentWebhookServiceImpl(PaymentWebhookLogRepository webhookLogRepository,
                                     PaymentTransactionRepository transactionRepository,
                                     UserRepository userRepository,
                                     ObjectMapper objectMapper,
                                     Pay2sProperties props) {
        this.webhookLogRepository = webhookLogRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.props = props;
    }

    @Override
    @Transactional
    public void processPay2sWebhook(String rawPayload, String clientIp, String authorization) {
        // 1. Luôn lưu lại raw payload để đối soát / debug về sau.
        PaymentWebhookLog webhookLog = new PaymentWebhookLog();
        webhookLog.setGateway("PAY2S");
        webhookLog.setRawPayload(rawPayload);
        webhookLog.setClientIp(clientIp);
        webhookLog.setProcessingStatus(ProcessingStatus.PENDING);
        webhookLog = webhookLogRepository.saveAndFlush(webhookLog);

        // 2. (Tùy chọn) Xác thực chữ ký nếu bật pay2s.verify-webhook-signature.
        if (props.isVerifyWebhookSignature()
                && !Pay2sSignatureUtil.isValidWebhookSignature(
                        rawPayload, props.getSecretKey(), authorization)) {
            log.warn("Webhook Pay2S sai chữ ký | IP: {}", clientIp);
            updateLogStatus(webhookLog, ProcessingStatus.IGNORED, "Invalid signature");
            return;
        }

        try {
            Pay2sWebhookRequest request =
                    objectMapper.readValue(rawPayload, Pay2sWebhookRequest.class);

            if (request.transactions() == null || request.transactions().isEmpty()) {
                updateLogStatus(webhookLog, ProcessingStatus.IGNORED, "No transactions in payload");
                return;
            }

            for (Pay2sTransaction txn : request.transactions()) {
                if ("IN".equalsIgnoreCase(txn.transferType())) {
                    processSingleTransaction(txn);
                }
            }

            updateLogStatus(webhookLog, ProcessingStatus.PROCESSED, null);
        } catch (Exception e) {
            log.error("Lỗi parse/xử lý webhook Pay2S", e);
            updateLogStatus(webhookLog, ProcessingStatus.FAILED, e.getMessage());
        }
    }

    private void processSingleTransaction(Pay2sTransaction txn) {
        // Khóa dòng để chặn 2 webhook trùng xử lý song song cùng một nội dung CK.
        PaymentTransaction target =
                transactionRepository.findByTransferContentForUpdate(txn.content()).orElse(null);

        if (target == null) {
            log.info("Không tìm thấy giao dịch chờ khớp cho nội dung CK: {}", txn.content());
            return;
        }

        if (target.getStatus() == TransactionStatus.SUCCESS) {
            log.warn("Giao dịch [{}] đã xử lý trước đó. Bỏ qua webhook trùng.", txn.content());
            return;
        }

        target.setBankTransactionCode(txn.transactionNumber());
        if (txn.transferAmount() != null) {
            target.setAmount(txn.transferAmount());
        }
        if (txn.transactionDate() != null) {
            try {
                target.setTransactionDate(LocalDateTime.parse(txn.transactionDate(), DATE_FMT));
            } catch (Exception ignored) {
                target.setTransactionDate(LocalDateTime.now());
            }
        }
        target.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.saveAndFlush(target);

        // Nâng gói cho user.
        User user = target.getUser();
        if (user != null && target.getTargetPlan() != null) {
            user.setPlan(target.getTargetPlan());
            userRepository.save(user);
            log.info("Đã nâng cấp user [{}] lên gói {} | CK: {}",
                    user.getId(), target.getTargetPlan(), txn.content());
        } else {
            log.error("Giao dịch [{}] thiếu user/targetPlan, không thể nâng gói.", txn.content());
        }
    }

    private void updateLogStatus(PaymentWebhookLog logRecord, ProcessingStatus status, String errorMsg) {
        logRecord.setProcessingStatus(status);
        logRecord.setErrorMessage(errorMsg);
        webhookLogRepository.save(logRecord);
    }
}
