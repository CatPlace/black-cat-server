package com.spring.blackcat.profile.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpsertProfileReqDto {
    private String introduce;

    private List<String> deleteImageUrls;
}
