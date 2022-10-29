package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.ProviderType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserLoginReqDto {
    private ProviderType providerType;

    private String providerToken;
}
