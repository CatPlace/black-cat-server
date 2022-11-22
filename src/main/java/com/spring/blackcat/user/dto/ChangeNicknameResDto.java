package com.spring.blackcat.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeNicknameResDto {
    private String nickname;

    public ChangeNicknameResDto(String nickname) {
        this.nickname = nickname;
    }
}
