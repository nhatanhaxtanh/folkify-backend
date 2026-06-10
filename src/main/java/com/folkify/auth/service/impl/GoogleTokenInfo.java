package com.folkify.auth.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleTokenInfo(
        String sub,
        String email,
        @JsonProperty("email_verified") String emailVerified,
        String name
) {}
