package com.folkify.security;

import com.folkify.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Request chưa xác thực (thiếu token hoặc token hết hạn) phải trả 401 để client biết đường
 * gọi refresh-token. Mặc định Spring Security dùng Http403ForbiddenEntryPoint và trả 403.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final SecurityErrorResponder responder;

    public RestAuthenticationEntryPoint(SecurityErrorResponder responder) {
        this.responder = responder;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        responder.write(response, ErrorCode.UNAUTHORIZED);
    }
}
