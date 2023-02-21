package com.spring.blackcat.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpsertProfileResDto {
    private Long profileId;
    private String introduce;
    private List<String> imageUrls;
}
