package com.spring.blackcat.tattoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTattoosByUserIdResDto {
    Long tattooId;
    String imageUrl;
}
