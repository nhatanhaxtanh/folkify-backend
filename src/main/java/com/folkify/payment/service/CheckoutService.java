package com.folkify.payment.service;

import com.folkify.auth.entity.User;
import com.folkify.payment.dto.request.CheckoutRequest;
import com.folkify.payment.dto.response.CheckoutResponse;
import com.folkify.payment.dto.response.PaymentStatusResponse;

public interface CheckoutService {

    /** Tạo link thanh toán Pay2S để user nâng cấp gói. */
    CheckoutResponse createCheckout(User currentUser, CheckoutRequest request);

    /** Poll trạng thái giao dịch theo orderId (app gọi định kỳ trong lúc chờ). */
    PaymentStatusResponse getStatus(User currentUser, String orderId);
}
