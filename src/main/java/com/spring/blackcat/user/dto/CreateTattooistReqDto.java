package com.spring.blackcat.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class CreateTattooistReqDto {
    @NotNull(message = "주소ID를 입력해주세요")
    private Long addressId;

    //    @NotNull(message = "오픈 채팅방 URL을 입력해주세요")
    private String openChatLink;
}
