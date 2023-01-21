package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResDto {
    private Long userId;
    private String accessToken;
    private Role role;
}
