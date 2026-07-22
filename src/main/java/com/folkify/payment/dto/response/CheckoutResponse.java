package com.folkify.payment.dto.response;

/** Trả về cho app: URL để mở WebView + orderId để poll trạng thái. */
public record CheckoutResponse(String payUrl, String orderId) {}
