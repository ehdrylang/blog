package com.example.blog.searcher.exception;

import com.example.blog.common.exception.BusinessException;
import com.example.blog.common.exception.ErrorCode;

public class UnsupportedEncodingException extends BusinessException {

    public UnsupportedEncodingException(Throwable e, ErrorCode errorCode) {
        super(e, errorCode);
    }
}
