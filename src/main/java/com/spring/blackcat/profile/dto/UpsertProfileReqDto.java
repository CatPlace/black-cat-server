package com.spring.blackcat.profile.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpsertProfileReqDto {
    String introduce;

    List<String> deleteImageUrls;
}
