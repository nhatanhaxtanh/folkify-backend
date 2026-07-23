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
@Tag(name = "Payment Webhook", description = "Hứng xác nhận thanh toán từ PayOS (Public API)")
public class PaymentWebhookController {

    private static final Logger log = LoggerFactory.getLogger(PaymentWebhookController.class);

    private final PaymentWebhookService paymentWebhookService;

    public PaymentWebhookController(PaymentWebhookService paymentWebhookService) {
        this.paymentWebhookService = paymentWebhookService;
    }

    @PostMapping("/payos")
    @Operation(summary = "Webhook PayOS",
            description = "PayOS gọi khi thanh toán hoàn tất. Chữ ký được xác thực bằng checksum key. "
                    + "Luôn trả HTTP 200 thật nhanh.")
    public ResponseEntity<Map<String, Object>> handlePayosWebhook(
            @RequestBody Object body,
            HttpServletRequest request) {

        String clientIp = request.getRemoteAddr();
        log.info("Nhận webhook từ PayOS | IP: {}", clientIp);

        try {
            paymentWebhookService.processPayosWebhook(body, clientIp);
        } catch (Exception e) {
            // Không ném lỗi ra ngoài để PayOS không retry dồn dập; đã có log lưu lại.
            log.error("Lỗi khi xử lý webhook PayOS", e);
        }

        return ResponseEntity.ok(Map.of("success", true, "message", "Webhook received successfully"));
    }
}
