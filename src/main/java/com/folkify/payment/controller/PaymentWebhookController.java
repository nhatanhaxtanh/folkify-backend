package com.folkify.payment.controller;

import com.folkify.payment.service.PaymentWebhookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments/webhooks")
@Tag(name = "Payment Webhook", description = "Hứng biến động số dư từ Pay2S (Public API)")
public class PaymentWebhookController {

    private static final Logger log = LoggerFactory.getLogger(PaymentWebhookController.class);

    private final PaymentWebhookService paymentWebhookService;

    public PaymentWebhookController(PaymentWebhookService paymentWebhookService) {
        this.paymentWebhookService = paymentWebhookService;
    }

    @PostMapping("/pay2s")
    @Operation(summary = "Webhook Pay2S",
            description = "Pay2S tự động gọi khi có tiền chuyển vào tài khoản. Luôn trả HTTP 200 thật nhanh.")
    public ResponseEntity<Map<String, Object>> handlePay2sWebhook(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody String rawPayload,
            HttpServletRequest request) {

        String clientIp = request.getRemoteAddr();
        log.info("Nhận webhook từ Pay2S | IP: {}", clientIp);

        try {
            paymentWebhookService.processPay2sWebhook(rawPayload, clientIp, authorization);
        } catch (Exception e) {
            // Không ném lỗi ra ngoài để Pay2S không retry dồn dập; đã có log lưu lại.
            log.error("Lỗi khi xử lý webhook Pay2S", e);
        }

        return ResponseEntity.ok(Map.of("success", true, "message", "Webhook received successfully"));
    }
}
