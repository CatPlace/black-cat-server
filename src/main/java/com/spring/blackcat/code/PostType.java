package com.spring.blackcat.code;

public enum PostType {
    PROFILE(Values.PROFILE), TATTOO(Values.TATTOO), MAGAZINE(Values.MAGAZINE);

    private PostType(String val) {
        // force equality between name of enum instance, and value of constant
        if (!this.name().equals(val))
            throw new IllegalArgumentException("Incorrect use of PostType");
    }

    public static class Values {
        public static final String PROFILE = "PROFILE";
        public static final String TATTOO = "TATTOO";
        public static final String MAGAZINE = "MAGAZINE";
    }
}
