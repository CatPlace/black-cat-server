package com.spring.blackcat.code;

public enum CellType {
    GRAYTEXTCELL(CellType.Values.GRAYTEXTCELL),
    TOGGLETEXTCELL(CellType.Values.TOGGLETEXTCELL),
    IMAGECELL(CellType.Values.IMAGECELL),
    LEFTCHATCELL(CellType.Values.LEFTCHATCELL);

    CellType(String val) {
        // force equality between name of enum instance, and value of constant
        if (!this.name().equals(val))
            throw new IllegalArgumentException("Incorrect use of CellType");
    }

    public static class Values {
        public static final String GRAYTEXTCELL = "GRAYTEXTCELL";
        public static final String TOGGLETEXTCELL = "TOGGLETEXTCELL";
        public static final String IMAGECELL = "IMAGECELL";
        public static final String LEFTCHATCELL = "LEFTCHATCELL";
    }
}
