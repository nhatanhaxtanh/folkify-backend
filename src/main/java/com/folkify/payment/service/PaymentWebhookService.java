package com.folkify.payment.service;

public interface PaymentWebhookService {

    /** Xử lý webhook biến động số dư từ Pay2S. */
    void processPay2sWebhook(String rawPayload, String clientIp, String authorization);
}
