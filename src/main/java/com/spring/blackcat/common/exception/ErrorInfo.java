package com.spring.blackcat.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorInfo {
    MAGAZINE_NOT_FOUND_EXCEPTION(400, 1000, "존재하지 않는 매거진 입니다."),
    USER_NOT_FOUND_EXCEPTION(400, 1001, "존재하지 않는 사용자 입니다."),
    TATTOO_NOT_FOUND_EXCEPTION(400, 1002, "존재하지 않는 타투 입니다."),
    CATEGORY_NOT_FOUND_EXCEPTION(400, 1003, "존재하지 않는 카테고리 입니다."),
    IMAGE_UPLOAD_FAILED(500, 1004, "이미지 업로드 실패"),
    INVALID_LOGIN_INPUT_EXCEPTION(400, 1005, "입력 값이 유효하지 않은 로그인 요청입니다."),
    INVALID_TOKEN_EXCEPTION(401, 1006, "유효하지 않은 토큰입니다."),
    ILEGAL_TOKEN_EXCEPTION(401, 1007, "토큰이 없거나 타입이 잘못되었습니다."),
    POST_TYPE_NOT_FOUND_EXCEPTION(400, 1008, "존재하지 않는 게시물 유형입니다.");


    private int statusCode;
    private int errorCode;
    private String message;
}
