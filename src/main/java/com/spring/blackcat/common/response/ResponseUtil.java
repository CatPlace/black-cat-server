package com.spring.blackcat.common.response;

public class ResponseUtil {
    public static <T> ResponseDto<T> SUCCESS(String message, T data) {
        return new ResponseDto(true, data, message);
    }
}
