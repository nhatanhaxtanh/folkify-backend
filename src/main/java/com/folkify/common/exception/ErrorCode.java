package com.folkify.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    SUCCESS(1000, "Thành công", HttpStatus.OK),

    // Auth (1001 - 1099)
    EMAIL_ALREADY_EXISTS(1001, "Email đã được sử dụng", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS(1002, "Email hoặc mật khẩu không đúng", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN(1003, "Refresh token không hợp lệ", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(1004, "Refresh token đã hết hạn hoặc bị thu hồi", HttpStatus.UNAUTHORIZED),

    // Instrument (1100 - 1199)
    INSTRUMENT_NOT_FOUND(1100, "Không tìm thấy nhạc cụ", HttpStatus.NOT_FOUND),
    LESSON_NOT_FOUND(1101, "Không tìm thấy bài học", HttpStatus.NOT_FOUND),
    LESSON_ALREADY_COMPLETED(1102, "Bài học đã được hoàn thành trước đó", HttpStatus.CONFLICT),

    // User (1200 - 1299)
    USER_NOT_FOUND(1200, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    WRONG_PASSWORD(1201, "Mật khẩu hiện tại không đúng", HttpStatus.BAD_REQUEST),

    // General (4000+)
    VALIDATION_ERROR(4000, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(4004, "Không tìm thấy tài nguyên", HttpStatus.NOT_FOUND),
    FORBIDDEN(4003, "Không có quyền truy cập", HttpStatus.FORBIDDEN),

    // Server (5000+)
    UNEXPECTED_ERROR(5000, "Đã xảy ra lỗi, vui lòng thử lại", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}
