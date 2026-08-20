package com.folkify.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkify.common.exception.ErrorCode;
import com.folkify.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/** Ghi lỗi bảo mật ra response dưới đúng định dạng ApiResponse như phần còn lại của API. */
@Component
public class SecurityErrorResponder {

    private final ObjectMapper objectMapper;

    public SecurityErrorResponder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void write(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), ApiResponse.error(errorCode));
    }
}
