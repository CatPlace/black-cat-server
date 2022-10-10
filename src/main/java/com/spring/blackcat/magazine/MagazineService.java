package com.spring.blackcat.magazine;

import java.util.List;

public interface MagazineService {

    List<MagazineTitleDto> findAll();

    List<CellDto> getMagazineCells(Long id);
}
