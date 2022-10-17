package com.spring.blackcat.magazine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagazineTitleReqDto {

    private String title;

    private String imageUrl;

    private List<CellDto> data;
}
