package com.folkify.payment.enumType;

public enum TransactionStatus {
    PENDING,    // Giao dịch mới tạo, đang chờ user quét mã / chuyển khoản.
    SUCCESS,    // Tiền đã vào tài khoản, webhook xác nhận khớp nội dung và đã nâng gói.
    CANCELLED,  // Giao dịch bị hủy do quá thời gian chờ.
    FAILED      // Webhook báo tiền về nhưng xử lý nghiệp vụ thất bại (cần xử lý thủ công).
}
