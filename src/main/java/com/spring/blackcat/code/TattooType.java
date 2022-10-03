package com.spring.blackcat.code;

public enum TattooType {
    DESIGN(Values.DESIGN), WORK(Values.WORK);

    private TattooType(String val) {
        // force equality between name of enum instance, and value of constant
        if (!this.name().equals(val))
            throw new IllegalArgumentException("Incorrect use of TattooType");
    }

    public static class Values {
        public static final String DESIGN = "DESIGN";
        public static final String WORK = "WORK";
    }
}
