package com.spring.blackcat.tattoo.dto;

import com.spring.blackcat.common.code.TattooType;
import lombok.Getter;

import java.util.List;

@Getter
public class GetTattooResDto extends GetTattoosResDto {
    private int likeCount;

    //@TODO: 타투이스트 이름 추가
    public GetTattooResDto(Long id, Long price, String tattooistName, String description, boolean isLiked, String address, List<String> imageUrls, TattooType tattooType, int likeCount) {
        super(id, price, tattooistName, description, isLiked, address, imageUrls, tattooType);
        this.likeCount = likeCount;
    }
}
