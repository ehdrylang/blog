package com.example.blog.common.exception;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(Throwable e, ErrorCode errorCode) {
        super(e);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
