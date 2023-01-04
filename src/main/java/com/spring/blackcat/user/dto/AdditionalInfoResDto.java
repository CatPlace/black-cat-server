package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdditionalInfoResDto {
    private String name;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private Long addressId;
}
