package com.example.blog.common.exception;

import com.example.blog.searcher.exception.UnsupportedEncodingException;
import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    INTERNAL_SERVER_ERROR(500, "C002", "Server Error"),
    INVALID_TYPE_VALUE(400, "C003", " Invalid Type Value"),

    // Search
    NO_SEARCH_SYSTEM_EXCEPTION(500, "S001", "Search system is not available"),
    UNSUPPORTED_ENCODING_EXCEPTION(500, "S002", "Unsupported Encoding Exception"),
    FAIL_INTEGRATION(500, "S003", "Fail integration");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}