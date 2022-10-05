package com.spring.blackcat.tattoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllTattoosResDto {
    private Long id;
    private Long price;
    private String tattooistName;
    private String description;
    private boolean isLiked;
}
