package com.spring.blackcat.magazine.dto;

import com.spring.blackcat.code.CellType;
import com.spring.blackcat.code.FontWeightType;
import com.spring.blackcat.code.TextAlignmentType;
import com.spring.blackcat.code.TextColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CellDto {
    private CellType cellType;

    private String text;

    private Long fontSize;

    private TextColor textColor;

    private TextAlignmentType textAlignment;

    private FontWeightType fontWeight;

    private String imageUrlString;

    private Long imageCornerRadius;

    private Long layoutHeight;

    private Long layoutWidth;

    private Long layoutLeadingInset;

    private Long layoutTrailingInset;

    private Long layoutTopInset;

    private Long layoutBottomInset;

}
