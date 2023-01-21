package com.spring.blackcat.common.code;

public enum ImageType {
    POST(ImageType.Values.POST), USER(ImageType.Values.USER);

    private ImageType(String val) {
        // force equality between name of enum instance, and value of constant
        if (!this.name().equals(val))
            throw new IllegalArgumentException("Incorrect use of PostType");
    }

    public static class Values {
        public static final String POST = "POST";
        public static final String USER = "USER";
    }
}
