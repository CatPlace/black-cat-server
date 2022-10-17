package com.spring.blackcat.magazine;

import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.MagazineNotFoundException;
import com.spring.blackcat.magazine.dto.CellDto;
import com.spring.blackcat.magazine.dto.MagazineTitleReqDto;
import com.spring.blackcat.magazine.dto.MagazineTitleResDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MagazineServiceImpl implements MagazineService {

    private final MagazineRepository magazineRepository;
    private final CellRepository cellRepository;

    private final ModelMapper modelMapper;

    // TODO: 2022-10-10 magazine title 가져올 때 얘가 메인인지 아닌지 파악

    @Override
    public Page<MagazineTitleResDto> findAll(Pageable pageable) {
        Page<Magazine> magazines = magazineRepository.findAll(pageable);
        return magazines.map(magazine -> modelMapper.map(magazine, MagazineTitleResDto.class));
    }

    @Override
    public List<CellDto> getMagazineCells(Long id) {
        Magazine magazine = magazineRepository
                .findById(id).orElseThrow(() -> new MagazineNotFoundException("존재하지 않는 매거진입니다.", ErrorInfo.MAGAZINE_NOT_FOUND_EXCEPTION));
        List<Cell> cells = cellRepository.findCellsByMagazine(magazine);
        List<CellDto> cellDtos = cells.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                .collect(Collectors.toList());
        return cellDtos;
    }

    @Override
    public MagazineTitleResDto postMagazine(MagazineTitleReqDto reqDto) {
        Magazine magazine = Magazine.builder()
                .title(reqDto.getTitle())
                .imageUrl(reqDto.getImageUrl())
                .isMain(true)
                .build();

        List<CellDto> cellDtos = reqDto.getData();

        List<Cell> cellList = cellDtos.stream().map(cellDto -> modelMapper.map(cellDto, Cell.class)).collect(Collectors.toList());

        cellList.forEach(cell -> cell.changeMagazine(magazine));

        List<Magazine> mainTrueList = magazineRepository.findAllByIsMainTrue();
        mainTrueList.forEach(m -> m.setIsMain(false));

        Magazine newMagazine = magazineRepository.save(magazine);

        return MagazineTitleResDto.builder().id(newMagazine.getId()).title(newMagazine.getTitle()).isMain(newMagazine.getIsMain()).imageUrl(newMagazine.getImageUrl()).build();
    }

}
