package com.spring.blackcat.tattoo.dto;


import com.spring.blackcat.common.code.TattooType;
import lombok.Getter;

@Getter
public class CreateTattooDto {
    TattooType tattooType;
    Long categoryId;
    String title;
    Long price;
    String description;
}
