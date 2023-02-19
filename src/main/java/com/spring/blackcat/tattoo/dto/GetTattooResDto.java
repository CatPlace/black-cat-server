package com.spring.blackcat.tattoo.dto;

import com.spring.blackcat.common.code.TattooType;
import lombok.Getter;

import java.util.List;

@Getter
public class GetTattooResDto extends GetTattoosResDto {
    private int likeCount;

    //@TODO: 타투이스트 이름 추가
    public GetTattooResDto(Long id, Long price, Long tattooistId, String tattooistName, String description, boolean isLiked,
                           String address, List<String> imageUrls, TattooType tattooType, Long categoryId, int likeCount) {
        super(id, price, tattooistId, tattooistName, description, address, imageUrls, tattooType, categoryId);
        this.likeCount = likeCount;
    }
}
