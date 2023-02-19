package com.spring.blackcat.tattoo.dto;

import com.spring.blackcat.common.code.TattooType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetTattoosResDto {
    private Long id;
    private String title;
    private Long price;
    private Long tattooistId;
    private String tattooistName;
    private String description;
    private String address;
    private List<String> imageUrls;
    private TattooType tattooType;
    private List<Long> categoryIds;
}
