package com.folkify.security;

import com.folkify.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/** Đã đăng nhập nhưng không đủ quyền — trả 403 kèm body ApiResponse thay vì trang lỗi mặc định. */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final SecurityErrorResponder responder;

    public RestAccessDeniedHandler(SecurityErrorResponder responder) {
        this.responder = responder;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        responder.write(response, ErrorCode.FORBIDDEN);
    }
}
