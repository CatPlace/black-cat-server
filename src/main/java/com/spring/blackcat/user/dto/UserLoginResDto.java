package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.Gender;
import com.spring.blackcat.common.code.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class UserLoginResDto {
    private Long userId;
    private String accessToken;
    private Role role;
    private Date dateOfBirth;
    private String email;
    private Gender gender;
    private String userName;
    private Long addressId;
}
