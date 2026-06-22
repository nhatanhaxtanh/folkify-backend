package com.folkify.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AppleAuthRequest(
        @NotBlank String identityToken,
        String fullName
) {}
