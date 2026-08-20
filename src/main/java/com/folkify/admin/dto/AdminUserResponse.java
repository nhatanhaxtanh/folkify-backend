package com.folkify.admin.dto;

import com.folkify.auth.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminUserResponse(
        UUID id,
        String name,
        String email,
        String role,
        String plan,
        /** Ngày thanh toán thành công gần nhất. NULL = chưa từng mua (gói tặng tay / FREE). */
        LocalDateTime lastPurchaseAt,
        /** Ngày hết hạn gói trả phí. NULL = không giới hạn (gói cũ / tài khoản FREE). */
        LocalDateTime planExpiresAt,
        LocalDateTime createdAt
) {
    public static AdminUserResponse from(User user, LocalDateTime lastPurchaseAt) {
        return new AdminUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getPlan().name(),
                lastPurchaseAt,
                user.getPlanExpiresAt(),
                user.getCreatedAt()
        );
    }
}
