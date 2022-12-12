package com.spring.blackcat.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorInfo {

    UNKNOWN_EXCEPTION(500, 9999, "알 수 없는 오류입니다."),
    MAGAZINE_NOT_FOUND_EXCEPTION(400, 1000, "존재하지 않는 매거진 입니다."),
    USER_NOT_FOUND_EXCEPTION(400, 1001, "존재하지 않는 사용자 입니다."),
    TATTOO_NOT_FOUND_EXCEPTION(400, 1002, "존재하지 않는 타투 입니다."),
    CATEGORY_NOT_FOUND_EXCEPTION(400, 1003, "존재하지 않는 카테고리 입니다."),
    IMAGE_UPLOAD_FAILED(500, 1004, "이미지 업로드 실패"),
    INVALID_LOGIN_INPUT_EXCEPTION(400, 1005, "입력 값이 유효하지 않은 로그인 요청입니다."),
    INVALID_TOKEN_EXCEPTION(401, 1006, "유효하지 않은 토큰입니다."),
    ILLEGAL_TOKEN_EXCEPTION(401, 1007, "토큰이 없거나 타입이 잘못되었습니다."),
    POST_TYPE_NOT_FOUND_EXCEPTION(400, 1008, "존재하지 않는 게시물 유형입니다."),
    UNAUTHORIZED_USER_EXCEPTION(403, 1009, "인가되지 않은 사용자입니다."),
    ALREADY_EXIST_ADDITIONAL_INFO_EXCEPTION(201, 1010, "이미 추가 정보를 입력한 사용자입니다."),
    NULL_INPUT_EXCEPTION(400, 1011, "필수값이 누락되었습니다."),
    INCORRECTLY_FORMATTED_FILE_EXCEPTION(400, 1012, "올바르지 않은 형식의 파일입니다."),
    FILE_RESIZING_FAILED_EXCEPTION(500, 1013, "파일 리사이징에 실패했습니다."),
    INVALID_INPUT_EXCEPTION(400, 1014, "올바르지 않은 입력값 형식입니다.");

    private final boolean success = false;
    private int statusCode;
    private int errorCode;
    private String message;
}
