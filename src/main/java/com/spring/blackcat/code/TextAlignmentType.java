package com.spring.blackcat.code;

public enum TextAlignmentType {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right"),
    JUSTIFIED("justified"),
    NATURAL("natural");

    private String value;

    TextAlignmentType(String value) {
        this.value = value;
    }
}
