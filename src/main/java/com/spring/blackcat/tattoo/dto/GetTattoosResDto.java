package com.spring.blackcat.tattoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetTattoosResDto {
    private Long id;
    private Long price;
    //@TODO: 타투이스트 이름 추가
//    private String tattooistName;
    private String description;
    private boolean isLiked;
    private String address;
    private List<String> imageUrls;
}
