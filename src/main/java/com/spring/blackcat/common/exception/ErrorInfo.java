package com.spring.blackcat.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorInfo {
    MAGAZINE_NOT_FOUND_EXCEPTION(400, 1000, "존재하지 않는 매거진 입니다."),
    USER_NOT_FOUND_EXCEPTION(400, 1001, "존재하지 않는 사용자 입니다."),
    TATTOO_NOT_FOUND_EXCEPTION(400, 1002, "존재하지 않는 타투 입니다."),
    CATEGORY_NOT_FOUND_EXCEPTION(400, 1002, "존재하지 않는 카테고리 입니다.");

    private int statusCode;
    private int errorCode;
    private String message;
}
