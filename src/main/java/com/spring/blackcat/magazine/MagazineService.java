package com.spring.blackcat.magazine;

import com.spring.blackcat.magazine.dto.CellDto;
import com.spring.blackcat.magazine.dto.MagazineTitleDto;

import java.util.List;

public interface MagazineService {

    List<MagazineTitleDto> findAll();

    List<CellDto> getMagazineCells(Long id);
}
