package com.spring.blackcat.magazine;

import com.spring.blackcat.code.CellType;
import com.spring.blackcat.code.FontWeightType;
import com.spring.blackcat.code.TextAlignmentType;
import com.spring.blackcat.code.TextColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@DynamicInsert
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cell_id")
    private Long id;

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
