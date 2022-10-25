package com.spring.blackcat.tattoo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GetTattooResDto extends GetTattoosResDto {
    private int likeCount;

    public GetTattooResDto(Long id, Long price, String tattooistName, String description, boolean isLiked, String address, List<String> imageUrls, int likeCount) {
        super(id, price, tattooistName, description, isLiked, address, imageUrls);
        this.likeCount = likeCount;
    }
}
