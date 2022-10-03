package com.spring.blackcat.code;

public enum Role {
    ADMIN(Values.ADMIN), TATTOOIST(Values.TATTOOIST), BASIC(Values.BASIC);

    private Role(String val) {
        // force equality between name of enum instance, and value of constant
        if (!this.name().equals(val))
            throw new IllegalArgumentException("Incorrect use of Role");
    }

    public static class Values {
        public static final String ADMIN = "ADMIN";
        public static final String TATTOOIST = "TATTOOIST";
        public static final String BASIC = "BASIC";
    }
}
