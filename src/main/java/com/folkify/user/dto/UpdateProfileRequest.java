package com.folkify.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @NotBlank(message = "Tên không được để trống")
        @Size(max = 100, message = "Tên không được quá 100 ký tự")
        String name
) {}
