package com.spring.blackcat.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResDto {
    private Long userId;
    private String accessToken;
}
