package com.folkify.payment.util;

import com.folkify.common.exception.ApiException;
import com.folkify.common.exception.ErrorCode;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public final class Pay2sSignatureUtil {

    private Pay2sSignatureUtil() {}

    /** Chữ ký khi tạo link thanh toán — HMAC-SHA256, thứ tự tham số A-Z theo docs Pay2S. */
    public static String generateSignature(
            String accessKey,
            String amount,
            String ipnUrl,
            String orderId,
            String orderInfo,
            String partnerCode,
            String redirectUrl,
            String requestId,
            String requestType,
            String secretKey) {
        String rawHash = String.format(
                "accessKey=%s&amount=%s&bankAccounts=Array&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                accessKey, amount, ipnUrl, orderId, orderInfo, partnerCode, redirectUrl, requestId, requestType);
        return hmacSha256(rawHash, secretKey);
    }

    /** Ký lại toàn bộ raw body để so khớp với header Authorization của webhook. */
    public static String calculateWebhookSignature(String rawData, String secretKey) {
        return hmacSha256(rawData, secretKey);
    }

    public static boolean isValidWebhookSignature(
            String rawData, String secretKey, String providedSignature) {
        if (providedSignature == null || providedSignature.isBlank()) {
            return false;
        }
        String calculated = calculateWebhookSignature(rawData, secretKey);
        String cleanProvided = providedSignature.replace("Bearer ", "").trim();
        return cleanProvided.equalsIgnoreCase(calculated);
    }

    private static String hmacSha256(String data, String secretKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString().toLowerCase();
        } catch (Exception e) {
            throw new ApiException(ErrorCode.UNEXPECTED_ERROR, "Lỗi tạo chữ ký bảo mật Pay2S");
        }
    }
}
