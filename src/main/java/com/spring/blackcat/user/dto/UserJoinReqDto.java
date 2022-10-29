package com.spring.blackcat.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserJoinReqDto {

    private String id;
    private String password;
    private String name;
}
