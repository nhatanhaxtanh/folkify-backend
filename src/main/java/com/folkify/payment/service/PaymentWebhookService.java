package com.folkify.payment.service;

public interface PaymentWebhookService {

    /** Xử lý webhook xác nhận thanh toán từ PayOS (đã kèm chữ ký trong payload). */
    void processPayosWebhook(Object body, String clientIp);
}
