package com.folkify.payment.dto.response;

import com.folkify.auth.entity.Plan;
import com.folkify.payment.enumType.TransactionStatus;

/** Kết quả poll trạng thái giao dịch. */
public record PaymentStatusResponse(
        String orderId,
        TransactionStatus status,
        Plan targetPlan) {}
