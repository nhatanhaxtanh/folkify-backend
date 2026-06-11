package com.folkify.auth.service.impl;

import com.folkify.auth.dto.*;
import com.folkify.auth.entity.PasswordResetToken;
import com.folkify.auth.entity.RefreshToken;
import com.folkify.auth.entity.User;
import com.folkify.auth.repository.PasswordResetTokenRepository;
import com.folkify.auth.repository.RefreshTokenRepository;
import com.folkify.auth.repository.UserRepository;
import com.folkify.auth.service.AuthService;
import com.folkify.auth.service.EmailService;
import com.folkify.auth.service.JwtService;
import com.folkify.common.exception.ApiException;
import com.folkify.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final long refreshTokenExpiration;
    private final long resetPasswordExpiration;
    private final RestClient restClient = RestClient.create();

    public AuthServiceImpl(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            EmailService emailService,
            @Value("${app.jwt.refresh-token-expiration}") long refreshTokenExpiration,
            @Value("${app.reset-password.expiration}") long resetPasswordExpiration
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.resetPasswordExpiration = resetPasswordExpiration;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (BadCredentialsException e) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_CREDENTIALS));
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse loginWithGoogle(GoogleAuthRequest request) {
        GoogleTokenInfo tokenInfo;
        try {
            tokenInfo = restClient.get()
                    .uri("https://oauth2.googleapis.com/tokeninfo?id_token={token}", request.idToken())
                    .retrieve()
                    .body(GoogleTokenInfo.class);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.GOOGLE_AUTH_FAILED);
        }

        if (tokenInfo == null || !"true".equals(tokenInfo.emailVerified())) {
            throw new ApiException(ErrorCode.GOOGLE_AUTH_FAILED);
        }

        User user = userRepository.findByEmail(tokenInfo.email())
                .orElseGet(() -> {
                    String name = tokenInfo.name() != null
                            ? tokenInfo.name()
                            : tokenInfo.email().split("@")[0];
                    return userRepository.save(User.builder()
                            .name(name)
                            .email(tokenInfo.email())
                            .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                            .build());
                });

        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken stored = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (!stored.isValid()) {
            refreshTokenRepository.delete(stored);
            throw new ApiException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        User user = stored.getUser();
        refreshTokenRepository.delete(stored);
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public void logout(String rawRefreshToken) {
        refreshTokenRepository.findByToken(rawRefreshToken).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            passwordResetTokenRepository.deleteAllByUser(user);
            String rawToken = UUID.randomUUID().toString();
            PasswordResetToken resetToken = PasswordResetToken.builder()
                    .token(rawToken)
                    .user(user)
                    .expiresAt(LocalDateTime.now().plusSeconds(resetPasswordExpiration / 1000))
                    .build();
            passwordResetTokenRepository.save(resetToken);
            emailService.sendPasswordResetEmail(user.getEmail(), rawToken);
        });
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.token())
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_RESET_TOKEN));

        if (resetToken.isUsed()) {
            throw new ApiException(ErrorCode.INVALID_RESET_TOKEN);
        }
        if (resetToken.isExpired()) {
            throw new ApiException(ErrorCode.RESET_TOKEN_EXPIRED);
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        refreshTokenRepository.deleteAllByUser(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String rawRefreshToken = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(rawRefreshToken)
                .user(user)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000))
                .build();
        refreshTokenRepository.save(refreshToken);

        return AuthResponse.of(
                accessToken,
                rawRefreshToken,
                jwtService.getAccessTokenExpiration() / 1000,
                UserDto.from(user)
        );
    }
}
