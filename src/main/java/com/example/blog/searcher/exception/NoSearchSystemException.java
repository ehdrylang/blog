package com.example.blog.searcher.exception;

import com.example.blog.common.exception.BusinessException;
import com.example.blog.common.exception.ErrorCode;

public class NoSearchSystemException extends BusinessException {

    public NoSearchSystemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
