package com.spring.blackcat.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeNicknameReqDto {
    private String nickname;

    public ChangeNicknameReqDto(String nickname) {
        this.nickname = nickname;
    }
}
