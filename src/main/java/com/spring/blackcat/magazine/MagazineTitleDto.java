package com.spring.blackcat.magazine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MagazineTitleDto {
    private String title;

    private String imageUrl;
}
