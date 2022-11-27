package com.spring.blackcat.common.exception.custom;

import com.spring.blackcat.common.exception.ErrorInfo;
import lombok.Getter;

@Getter
public class AlreadyExistAdditionalInfoException extends CustomException {
    private ErrorInfo errorInfo;

    public AlreadyExistAdditionalInfoException(String message, ErrorInfo errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }
}
