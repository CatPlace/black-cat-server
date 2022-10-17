package com.spring.blackcat.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int statusCode;
    private int errorCode;
    private String message;

    public ErrorResponse(ErrorInfo errorInfo) {
        this.statusCode = errorInfo.getStatusCode();
        this.errorCode = errorInfo.getErrorCode();
        this.message = errorInfo.getMessage();
    }
}
