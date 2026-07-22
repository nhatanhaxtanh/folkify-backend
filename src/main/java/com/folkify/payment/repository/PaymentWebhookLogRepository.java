package com.folkify.payment.repository;

import com.folkify.payment.entity.PaymentWebhookLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentWebhookLogRepository extends JpaRepository<PaymentWebhookLog, UUID> {}
