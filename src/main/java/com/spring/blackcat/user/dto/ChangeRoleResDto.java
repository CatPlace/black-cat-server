package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeRoleResDto {
    private Long userId;
    private Role role;

    public ChangeRoleResDto(Long userId, Role role) {
        this.userId = userId;
        this.role = role;
    }
}
