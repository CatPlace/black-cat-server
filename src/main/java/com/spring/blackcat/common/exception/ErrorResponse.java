package com.spring.blackcat.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private boolean success;

    @JsonIgnore
    private int statusCode;
    private int errorCode;
    private String message;

    public ErrorResponse(ErrorInfo errorInfo) {
        this.success = false;
        this.statusCode = errorInfo.getStatusCode();
        this.errorCode = errorInfo.getErrorCode();
        this.message = errorInfo.getMessage();
    }
}
