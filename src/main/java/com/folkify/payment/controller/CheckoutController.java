package com.folkify.payment.controller;

import com.folkify.auth.entity.User;
import com.folkify.common.response.ApiResponse;
import com.folkify.payment.dto.request.CheckoutRequest;
import com.folkify.payment.dto.response.CheckoutResponse;
import com.folkify.payment.dto.response.PaymentStatusResponse;
import com.folkify.payment.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment Checkout", description = "Nâng cấp gói qua cổng thanh toán Pay2S")
@SecurityRequirement(name = "Bearer Authentication")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    @Operation(summary = "Tạo link thanh toán Pay2S để nâng cấp gói",
            description = "App gửi gói muốn mua (BASIC/PRO). Trả về payUrl để mở WebView cho user quét QR, "
                    + "kèm orderId để poll trạng thái.")
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkout(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody CheckoutRequest request) {
        CheckoutResponse result = checkoutService.createCheckout(currentUser, request);
        return ResponseEntity.ok(ApiResponse.success("Tạo link thanh toán thành công", result));
    }

    @GetMapping("/status/{orderId}")
    @Operation(summary = "Kiểm tra trạng thái giao dịch",
            description = "App gọi định kỳ trong lúc chờ user thanh toán để biết đã thành công chưa.")
    public ResponseEntity<ApiResponse<PaymentStatusResponse>> status(
            @AuthenticationPrincipal User currentUser,
            @PathVariable String orderId) {
        return ResponseEntity.ok(ApiResponse.success(checkoutService.getStatus(currentUser, orderId)));
    }

    @GetMapping("/result")
    @Operation(summary = "Redirect sau khi thanh toán (Pay2S gọi)",
            description = "Chuyển hướng về deep link của app Folkify kèm các tham số Pay2S trả về.")
    public ResponseEntity<Void> paymentResult(@RequestParam Map<String, String> params) {
        UriComponentsBuilder deepLink = UriComponentsBuilder.newInstance()
                .uri(URI.create("folkify://payment/result"));
        params.forEach(deepLink::queryParam);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(deepLink.build().toUri());
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
