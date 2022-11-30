package com.spring.blackcat.tattoo.dto;


import com.spring.blackcat.common.code.TattooType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateTattooDto {

    @NotNull(message = "타투 종류를 입력해주세요")
    TattooType tattooType;

    @NotNull(message = "카테고리 ID를 입력해주세요")
    Long categoryId;

    @NotNull(message = "타투 제목을 입력해주세요")
    String title;

    @NotNull(message = "타투 가격을 입력해주세요")
    Long price;

    String description;
}
