package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Gender;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;

@Data
@RequiredArgsConstructor
public class AdditionalInfoReqDto {
    private String name;

    @Email(message = "올바르지 않은 이메일 형식입니다")
    private String email;

    private String phoneNumber;

    private Gender gender;
    
    private Long addressId;
}
