package com.example.blog.searcher.exception;

import com.example.blog.common.exception.BusinessException;
import com.example.blog.common.exception.ErrorCode;

public class UnsupportedEncodingException extends BusinessException {
    public UnsupportedEncodingException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UnsupportedEncodingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
