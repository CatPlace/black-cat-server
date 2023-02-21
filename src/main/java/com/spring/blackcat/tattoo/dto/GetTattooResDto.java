package com.spring.blackcat.tattoo.dto;

import com.spring.blackcat.common.code.TattooType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetTattooResDto extends GetTattoosResDto {
    
    private int likeCount;
    private boolean isLiked;
    private LocalDateTime createDate;
    private List<String> profileImageUrls;


    //@TODO: 타투이스트 이름 추가
    public GetTattooResDto(Long id, String title, Long price, Long tattooistId, String tattooistName, String description, boolean isLiked,
                           String address, List<String> imageUrls, TattooType tattooType, List<Long> categoryIds, int likeCount, LocalDateTime createDate, List<String> profileImageUrls) {
        super(id, title, price, tattooistId, tattooistName, description, address, imageUrls, tattooType, categoryIds);
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.createDate = createDate;
        this.profileImageUrls = profileImageUrls;
    }
}
