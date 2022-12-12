package com.spring.blackcat.common.exception.custom;

import com.spring.blackcat.common.exception.ErrorInfo;
import lombok.Getter;

@Getter
public class IncorrectlyFormattedFileException extends CustomException {
    private ErrorInfo errorInfo;

    public IncorrectlyFormattedFileException(String message, ErrorInfo errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }
}
