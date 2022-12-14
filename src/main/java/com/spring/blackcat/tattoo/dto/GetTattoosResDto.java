package com.spring.blackcat.tattoo.dto;

import com.spring.blackcat.common.code.TattooType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetTattoosResDto {
    private Long id;
    private Long price;
    private String tattooistName;
    private String description;
    private boolean isLiked;
    private String address;
    private List<String> imageUrls;
    private TattooType tattooType;
}
