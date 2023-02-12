package com.spring.blackcat.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE, FEMALE;

    @JsonCreator
    public static Gender forName(String name) {
        for (Gender g : values()) {
            if (g.name().equals(name)) {
                return g;
            }
        }

        return null;
    }
}
