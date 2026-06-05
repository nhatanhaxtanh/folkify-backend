package com.folkify.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.folkify.common.exception.ErrorCode;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int code;
    private String message;
    private T result;
    private Map<String, String> errors;

    private ApiResponse() {}

    public static <T> ApiResponse<T> success(T result) {
        ApiResponse<T> res = new ApiResponse<>();
        res.code = ErrorCode.SUCCESS.getCode();
        res.message = ErrorCode.SUCCESS.getMessage();
        res.result = result;
        return res;
    }

    public static <T> ApiResponse<T> success(String message, T result) {
        ApiResponse<T> res = new ApiResponse<>();
        res.code = ErrorCode.SUCCESS.getCode();
        res.message = message;
        res.result = result;
        return res;
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        ApiResponse<T> res = new ApiResponse<>();
        res.code = errorCode.getCode();
        res.message = errorCode.getMessage();
        return res;
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.code = errorCode.getCode();
        res.message = message;
        return res;
    }

    public static <T> ApiResponse<T> validationError(Map<String, String> errors) {
        ApiResponse<T> res = new ApiResponse<>();
        res.code = ErrorCode.VALIDATION_ERROR.getCode();
        res.message = ErrorCode.VALIDATION_ERROR.getMessage();
        res.errors = errors;
        return res;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public T getResult() { return result; }
    public Map<String, String> getErrors() { return errors; }
}
