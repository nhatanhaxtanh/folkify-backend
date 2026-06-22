package com.folkify.auth.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
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
    private final String appleBundleId;
    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthServiceImpl(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            EmailService emailService,
            @Value("${app.jwt.refresh-token-expiration}") long refreshTokenExpiration,
            @Value("${app.reset-password.expiration}") long resetPasswordExpiration,
            @Value("${app.apple.bundle-id:com.folkify.app}") String appleBundleId
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
        this.appleBundleId = appleBundleId;
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
    public AuthResponse loginWithApple(AppleAuthRequest request) {
        String[] parts = request.identityToken().split("\\.");
        if (parts.length != 3) throw new ApiException(ErrorCode.APPLE_AUTH_FAILED);

        String kid;
        try {
            JsonNode header = objectMapper.readTree(Base64.getUrlDecoder().decode(parts[0]));
            kid = header.get("kid").asText();
        } catch (Exception e) {
            throw new ApiException(ErrorCode.APPLE_AUTH_FAILED);
        }

        AppleKeysResponse keysResponse;
        try {
            keysResponse = restClient.get()
                    .uri("https://appleid.apple.com/auth/keys")
                    .retrieve()
                    .body(AppleKeysResponse.class);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.APPLE_AUTH_FAILED);
        }

        if (keysResponse == null) throw new ApiException(ErrorCode.APPLE_AUTH_FAILED);

        AppleKey matchingKey = keysResponse.keys().stream()
                .filter(k -> k.kid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorCode.APPLE_AUTH_FAILED));

        PublicKey publicKey;
        try {
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(matchingKey.n()));
            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(matchingKey.e()));
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (Exception e) {
            throw new ApiException(ErrorCode.APPLE_AUTH_FAILED);
        }

        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(request.identityToken())
                    .getPayload();
        } catch (Exception e) {
            throw new ApiException(ErrorCode.APPLE_AUTH_FAILED);
        }

        String iss = claims.getIssuer();
        boolean audMatches = claims.getAudience() != null && claims.getAudience().contains(appleBundleId);
        if (!"https://appleid.apple.com".equals(iss) || !audMatches) {
            throw new ApiException(ErrorCode.APPLE_AUTH_FAILED);
        }

        String appleSub = claims.getSubject();
        String email = claims.get("email", String.class);
        if (appleSub == null || email == null) throw new ApiException(ErrorCode.APPLE_AUTH_FAILED);

        User user = userRepository.findByAppleSub(appleSub)
                .orElseGet(() -> userRepository.findByEmail(email)
                        .map(u -> { u.setAppleSub(appleSub); return userRepository.save(u); })
                        .orElseGet(() -> {
                            String name = (request.fullName() != null && !request.fullName().isBlank())
                                    ? request.fullName()
                                    : email.split("@")[0];
                            return userRepository.save(User.builder()
                                    .name(name)
                                    .email(email)
                                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                                    .appleSub(appleSub)
                                    .build());
                        }));

        return buildAuthResponse(user);
    }

    private record AppleKey(String kty, String kid, String use, String alg, String n, String e) {}
    private record AppleKeysResponse(List<AppleKey> keys) {}

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
