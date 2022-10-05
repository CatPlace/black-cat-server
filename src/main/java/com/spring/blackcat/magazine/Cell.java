package com.spring.blackcat.magazine;

import com.spring.blackcat.code.CellType;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cell_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CellType cellType;
    @Column(length = 5000)
    private String content;

    @ManyToOne(fetch = EAGER)
//    @JoinColumn(name = "post_id")
    private Magazine magazine;

    @Builder
    public Cell(Long id, CellType cellType, String content) {
        this.id = id;
        this.cellType = cellType;
        this.content = content;
    }

    public Cell(Magazine magazine){
        this.magazine = magazine;
    }

    public Cell(Long id, CellType cellType, String content, Magazine magazine) {
        this.id = id;
        this.cellType = cellType;
        this.content = content;
        this.magazine = magazine;
        this.magazine.getCellList().add(this);
    }

    public void addMagazine(Magazine magazine) {
        magazine.getCellList().remove(this);
        this.magazine = magazine;
        magazine.getCellList().add(this);
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
}
