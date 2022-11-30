package com.spring.blackcat.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTattooistResDto {
    private Long addressId;

    private String openChatLink;
}
