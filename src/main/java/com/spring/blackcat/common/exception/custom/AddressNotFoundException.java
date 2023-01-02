package com.spring.blackcat.common.exception.custom;

import com.spring.blackcat.common.exception.ErrorInfo;
import lombok.Getter;

@Getter
public class AddressNotFoundException extends CustomException {
    private ErrorInfo errorInfo;

    public AddressNotFoundException(String message, ErrorInfo errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }
}
