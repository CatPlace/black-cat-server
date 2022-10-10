package com.spring.blackcat.magazine;

import com.spring.blackcat.code.CellType;
import com.spring.blackcat.code.FontWeightType;
import com.spring.blackcat.code.TextAlignmentType;
import com.spring.blackcat.code.TextColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cell_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CellType cellType;

    @Column(length = 5000)
    private String text;

    private Long fontSize;

    @Enumerated(EnumType.STRING)
    private TextColor textColor;

    @Enumerated(EnumType.STRING)
    private TextAlignmentType textAlignment;

    @Enumerated(EnumType.STRING)
    private FontWeightType fontWeight;

    private String imageUrlString;

    private Long imageCornerRadius;

    private Long layoutHeight;

    private Long layoutWidth;

    private Long layoutLeadingInset;

    private Long layoutTrailingInset;

    private Long layoutTopInset;

    private Long layoutBottomInset;

    @ManyToOne(fetch = EAGER)
    private Magazine magazine;

    public void changeMagazine(Magazine magazine) {
        if (this.magazine != null) {
            this.magazine.getCellList().remove(this);
        }
        this.magazine = magazine;
        magazine.getCellList().add(this);
    }
}
