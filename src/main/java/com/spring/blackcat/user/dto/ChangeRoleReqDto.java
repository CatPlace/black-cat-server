package com.spring.blackcat.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeRoleReqDto {
    private String role;

    public ChangeRoleReqDto(String role) {
        this.role = role;
    }
}
