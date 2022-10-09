package com.spring.blackcat.code;

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
