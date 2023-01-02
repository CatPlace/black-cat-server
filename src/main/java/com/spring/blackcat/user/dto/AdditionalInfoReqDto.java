package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Gender;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class AdditionalInfoReqDto {
    @NotNull(message = "이름을 입력해주세요")
    private String name;

    @NotNull(message = "이메일을 입력해주세요")
    @Email(message = "올바르지 않은 이메일 형식입니다")
    private String email;

    @NotNull(message = "전화번호를 입력해주세요")
    private String phoneNumber;

    @NotNull(message = "성별을 입력해주세요")
    private Gender gender;

    @NotNull(message = "지역을 입력해주세요")
    private Long addressId;
}
