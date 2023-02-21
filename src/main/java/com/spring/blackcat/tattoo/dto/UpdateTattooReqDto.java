package com.spring.blackcat.tattoo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateTattooReqDto extends CreateTattooDto {
    private List<String> deleteImageUrls;
}
