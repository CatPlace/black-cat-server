package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Gender;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@RequiredArgsConstructor
public class AdditionalInfoReqDto {
    @NotNull(message = "닉네임을 입력해주세요")
    private String nickname;

    @NotNull(message = "생년월일을 입력해주세요")
    private Date dateOfBirth;

    @NotNull(message = "성별을 입력해주세요")
    private Gender gender;
}
