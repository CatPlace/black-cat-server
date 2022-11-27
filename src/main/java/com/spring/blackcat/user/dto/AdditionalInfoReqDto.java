package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Gender;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@RequiredArgsConstructor
public class AdditionalInfoReqDto {
    private String nickname;

    private Date dateOfBirth;

    private Gender gender;
}
