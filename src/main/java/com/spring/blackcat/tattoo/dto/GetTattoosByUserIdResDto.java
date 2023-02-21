package com.spring.blackcat.tattoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTattoosByUserIdResDto {
    private Long tattooId;
    private String imageUrl;
}
