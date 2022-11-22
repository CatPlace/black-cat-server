package com.spring.blackcat.common.exception.custom;

import com.spring.blackcat.common.exception.ErrorInfo;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ErrorInfo errorInfo;

    public CustomException(String message) {
        super(message);
    }
}
