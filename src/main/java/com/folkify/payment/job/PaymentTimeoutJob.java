package com.folkify.payment.job;

import com.folkify.payment.entity.PaymentTransaction;
import com.folkify.payment.enumType.TransactionStatus;
import com.folkify.payment.repository.PaymentTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/** Hủy các giao dịch PENDING quá 15 phút để giải phóng nội dung CK đang chờ khớp. */
@Component
public class PaymentTimeoutJob {

    private static final Logger log = LoggerFactory.getLogger(PaymentTimeoutJob.class);

    private final PaymentTransactionRepository transactionRepository;

    public PaymentTimeoutJob(PaymentTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Scheduled(cron = "0 * * * * *") // mỗi phút
    @Transactional
    public void scanAndExpirePayments() {
        LocalDateTime timeoutLimit = LocalDateTime.now().minusMinutes(15);

        List<PaymentTransaction> expired =
                transactionRepository.findByStatusAndCreatedAtBefore(
                        TransactionStatus.PENDING, timeoutLimit);

        if (expired.isEmpty()) {
            return;
        }

        log.info("Phát hiện {} giao dịch PENDING quá hạn. Chuyển sang CANCELLED...", expired.size());
        for (PaymentTransaction txn : expired) {
            txn.setStatus(TransactionStatus.CANCELLED);
        }
        transactionRepository.saveAll(expired);
    }
}
