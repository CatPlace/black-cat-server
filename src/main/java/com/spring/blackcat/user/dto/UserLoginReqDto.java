package com.spring.blackcat.user.dto;

import com.spring.blackcat.common.code.ProviderType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class UserLoginReqDto {
    @NotNull(message = "프로바이더명을 입력해주세요")
    private ProviderType providerType;

    @NotNull(message = "프로바이더 토큰을 입력해주세요")
    private String providerToken;
}
