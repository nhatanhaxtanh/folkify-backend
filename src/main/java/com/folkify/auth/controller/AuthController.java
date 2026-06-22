package com.folkify.auth.controller;

import com.folkify.auth.dto.*;
import com.folkify.auth.service.AuthService;
import com.folkify.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API xác thực người dùng")
public class AuthController {

    private final AuthService authService;
    private final String deepLinkBaseUrl;

    public AuthController(
            AuthService authService,
            @Value("${app.deep-link.base-url}") String deepLinkBaseUrl
    ) {
        this.authService = authService;
        this.deepLinkBaseUrl = deepLinkBaseUrl;
    }

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản mới")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse data = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Đăng ký thành công", data));
    }

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", authService.login(request)));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Làm mới access token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.refreshToken(request)));
    }

    @PostMapping("/google")
    @Operation(summary = "Đăng nhập bằng Google")
    public ResponseEntity<ApiResponse<AuthResponse>> loginWithGoogle(@Valid @RequestBody GoogleAuthRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập Google thành công", authService.loginWithGoogle(request)));
    }

    @PostMapping("/apple")
    @Operation(summary = "Đăng nhập bằng Apple")
    public ResponseEntity<ApiResponse<AuthResponse>> loginWithApple(@Valid @RequestBody AppleAuthRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập Apple thành công", authService.loginWithApple(request)));
    }

    @PostMapping("/logout")
    @Operation(summary = "Đăng xuất")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.success("Đăng xuất thành công", null));
    }

    @GetMapping(value = "/reset-password/open", produces = MediaType.TEXT_HTML_VALUE)
    @Operation(summary = "Mở app đặt lại mật khẩu qua deep link")
    public ResponseEntity<String> openResetPassword(@RequestParam String token) {
        String deepLink = deepLinkBaseUrl + "?token=" + token;
        String html = """
                <!DOCTYPE html>
                <html lang="vi">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>Folkify</title>
                  <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body { background: #0f0f1a; color: #fff; font-family: 'Segoe UI', Arial, sans-serif;
                           display: flex; align-items: center; justify-content: center; min-height: 100vh; }
                    .card { background: #1a1a2e; border-radius: 20px; padding: 40px 32px;
                            text-align: center; max-width: 360px; width: 90%%; border: 1px solid #2a2a4a; }
                    .logo { color: #D97706; font-size: 26px; font-weight: 900; margin-bottom: 24px; }
                    .icon { font-size: 48px; margin-bottom: 16px; }
                    h2 { font-size: 20px; margin-bottom: 10px; }
                    p { color: #aaaacc; font-size: 14px; line-height: 1.6; margin-bottom: 28px; }
                    .btn { display: inline-block; background: #D97706; color: #fff; text-decoration: none;
                           padding: 14px 32px; border-radius: 12px; font-weight: 700; font-size: 15px; }
                  </style>
                  <script>
                    window.onload = function() {
                      window.location.href = '%s';
                      setTimeout(function() {
                        document.getElementById('fallback').style.display = 'block';
                      }, 2000);
                    };
                  </script>
                </head>
                <body>
                  <div class="card">
                    <div class="logo">&#9835; Folkify</div>
                    <div class="icon">&#128274;</div>
                    <h2>Đang mở ứng dụng...</h2>
                    <p>Vui lòng chờ trong giây lát.</p>
                    <a id="fallback" href="%s" class="btn" style="display:none;">Mở Folkify</a>
                  </div>
                </body>
                </html>
                """.formatted(deepLink, deepLink);
        return ResponseEntity.ok(html);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Yêu cầu đặt lại mật khẩu")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Nếu email tồn tại, hướng dẫn đặt lại mật khẩu đã được gửi", null));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Đặt lại mật khẩu bằng token")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Đặt lại mật khẩu thành công", null));
    }
}
