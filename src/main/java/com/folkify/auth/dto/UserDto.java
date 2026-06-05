package com.folkify.auth.dto;

import com.folkify.auth.entity.User;

import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String email,
        String role
) {
    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
    }
}
