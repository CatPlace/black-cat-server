package com.spring.blackcat.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeRoleResDto {
    private String role;

    public ChangeRoleResDto(String role) {
        this.role = role;
    }
}
