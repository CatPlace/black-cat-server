package com.spring.blackcat.tattoo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GetTattooResDto extends GetTattoosResDto {
    private int likeCount;

    //@TODO: 타투이스트 이름 추가
    public GetTattooResDto(Long id, Long price, String description, boolean isLiked, String address, List<String> imageUrls, int likeCount) {
        super(id, price, description, isLiked, address, imageUrls);
        this.likeCount = likeCount;
    }
}
