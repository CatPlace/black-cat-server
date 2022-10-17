package com.spring.blackcat.common.exception.custom;

import com.spring.blackcat.common.exception.ErrorInfo;
import lombok.Getter;

@Getter
public class MagazineNotFoundException extends RuntimeException {
    private ErrorInfo errorInfo;

    public MagazineNotFoundException(String message, ErrorInfo errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }
}