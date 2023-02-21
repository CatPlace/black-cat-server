package com.spring.blackcat.tattoo.dto;


import com.spring.blackcat.common.code.TattooType;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CreateTattooDto {
    @NotNull(message = "타투 종류를 입력해주세요")
    private TattooType tattooType;

    @NotNull(message = "카테고리 ID를 입력해주세요")
    private List<Long> categoryIds;

    @NotNull(message = "타투 제목을 입력해주세요")
    private String title;

    @NotNull(message = "타투 가격을 입력해주세요")
    private Long price;

    private String description;
}
