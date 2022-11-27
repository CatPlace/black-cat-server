package com.spring.blackcat.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateTattooistReqDto {
    private Long addressId;

    private String openChatLink;
}
