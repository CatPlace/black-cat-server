package com.spring.blackcat.magazine;

import com.spring.blackcat.magazine.dto.CellDto;
import com.spring.blackcat.magazine.dto.MagazineTitleReqDto;
import com.spring.blackcat.magazine.dto.MagazineTitleResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MagazineService {

    Page<MagazineTitleResDto> findAll(Pageable pageable);

    List<CellDto> getMagazineCells(Long id);

    MagazineTitleResDto postMagazine(MagazineTitleReqDto reqDto);
}
