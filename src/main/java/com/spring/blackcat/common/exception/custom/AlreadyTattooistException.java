package com.spring.blackcat.common.exception.custom;

import com.spring.blackcat.common.exception.ErrorInfo;
import lombok.Getter;

@Getter
public class AlreadyTattooistException extends CustomException {
    private ErrorInfo errorInfo;

    public AlreadyTattooistException(String message, ErrorInfo errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }
}
