package com.spring.blackcat.common.exception.custom;

import com.spring.blackcat.common.exception.ErrorInfo;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {
    private ErrorInfo errorInfo;

    public PostNotFoundException(String message, ErrorInfo errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }
}
