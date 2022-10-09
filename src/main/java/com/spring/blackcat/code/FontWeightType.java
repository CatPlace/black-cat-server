package com.spring.blackcat.code;

// TODO: 2022-10-10 Enum으로 관리되는 것들이 DB에 저장될 때 대문자말고 camelCase로 저장되도록
public enum FontWeightType {
    BOLD("bold"),
    BLACK("black"),
    MEDIUM("medium"),
    HEAVY("heavy"),
    REGULAR("regular"),
    SEMIBOLD("semiBold"),
    THIN("thin"),
    ULTRALIGHT("ultraLight");

    private String value;

    FontWeightType(String value) {
        this.value = value;
    }
}
