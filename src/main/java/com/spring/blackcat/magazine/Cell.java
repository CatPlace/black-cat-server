package com.spring.blackcat.magazine;

import com.spring.blackcat.code.CellType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private String textColor;

    private String textAlignment;

    private String fontWeight;

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

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
}
