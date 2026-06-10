package com.folkify.auth.service;

import com.folkify.auth.dto.AuthResponse;
import com.folkify.auth.dto.GoogleAuthRequest;
import com.folkify.auth.dto.LoginRequest;
import com.folkify.auth.dto.RefreshTokenRequest;
import com.folkify.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse loginWithGoogle(GoogleAuthRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    void logout(String refreshToken);
}
