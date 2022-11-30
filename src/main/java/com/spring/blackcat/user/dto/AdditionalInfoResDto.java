package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class AdditionalInfoResDto {
    private String nickname;

    private Date dateOfBirth;

    private Gender gender;
}
