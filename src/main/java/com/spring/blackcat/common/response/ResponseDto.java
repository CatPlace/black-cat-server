package com.spring.blackcat.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private final boolean success;
    private final T data;
    private final String message;
}
