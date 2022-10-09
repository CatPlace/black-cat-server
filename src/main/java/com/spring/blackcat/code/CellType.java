package com.spring.blackcat.code;

public enum CellType {
    TEXTCELL("textCell"),
    IMAGECELL("imageCell"),
    BULLETEDCELL("bulletedCell"),
    EMPTYCELL("emptyCell"),
    STORYSHARECELL("storyShareCell");

    private String value;

    CellType(String value) {
        this.value = value;
    }
}
