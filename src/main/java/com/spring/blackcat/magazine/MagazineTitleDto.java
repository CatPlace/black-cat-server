package com.spring.blackcat.magazine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MagazineTitleDto {

    private Long id;

    private String title;

    private String imageUrl;
    // TODO: 2022-10-10 메인 노출 여부 추가 
}
