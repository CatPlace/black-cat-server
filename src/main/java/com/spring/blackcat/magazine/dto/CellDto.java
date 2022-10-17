package com.spring.blackcat.magazine.dto;

import com.spring.blackcat.common.code.CellType;
import com.spring.blackcat.common.code.FontWeightType;
import com.spring.blackcat.common.code.TextAlignmentType;
import com.spring.blackcat.common.code.TextColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CellDto {

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CellType cellType = CellType.EMPTYCELL;

    @Builder.Default
    @Column(length = 5000)
    private String text = null;

    @Builder.Default
    private Long fontSize = 12L;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TextColor textColor = TextColor.BLACK;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TextAlignmentType textAlignment = TextAlignmentType.LEFT;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FontWeightType fontWeight = FontWeightType.REGULAR;

    @Builder.Default
    private String imageUrlString = null;

    @Builder.Default
    private Long imageCornerRadius = 0L;

    @Builder.Default
    private Long layoutHeight = 10L;

    @Builder.Default
    private Long layoutWidth = 10L;

    @Builder.Default
    private Long layoutLeadingInset = 20L;

    @Builder.Default
    private Long layoutTrailingInset = 20L;

    @Builder.Default
    private Long layoutTopInset = 20L;

    @Builder.Default
    private Long layoutBottomInset = 20L;

}
