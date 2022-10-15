package com.spring.blackcat.magazine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagazineTitleResDto {

    private Long id;

    private String title;

    private String imageUrl;

    private Boolean isMain;
}
