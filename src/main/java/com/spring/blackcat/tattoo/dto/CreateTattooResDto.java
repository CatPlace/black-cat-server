package com.spring.blackcat.tattoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateTattooResDto {
    private Long tattooId;
    private List<String> imageUrls;
}
