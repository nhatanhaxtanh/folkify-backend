package com.folkify.payment.dto.request;

import java.util.List;

/** Payload gửi sang Pay2S để tạo link thanh toán (giữ đúng schema Pay2S). */
public record Pay2sCreateLinkRequest(
        String accessKey,
        String partnerCode,
        String partnerName,
        String requestId,
        Long amount,
        String orderId,
        String orderInfo,
        String orderType,
        List<BankAccountPayload> bankAccounts,
        String redirectUrl,
        String ipnUrl,
        String requestType,
        String signature) {

    public record BankAccountPayload(String account_number, String bank_id) {}
}
