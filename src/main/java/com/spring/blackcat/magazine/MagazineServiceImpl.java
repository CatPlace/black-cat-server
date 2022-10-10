package com.spring.blackcat.magazine;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MagazineServiceImpl implements MagazineService{

    private final MagazineRepository magazineRepository;
    private final CellRepository cellRepository;

    private final ModelMapper modelMapper;

    // TODO: 2022-10-10 magazine title 가져올 때 얘가 메인인지 아닌지 파악
    // TODO: 2022-10-10 magazine title 가져올 때 pagable 적용해야 될듯
    @Override
    public List<MagazineTitleDto> findAll() {
        List<Magazine> magazines = magazineRepository.findAll();
        List<MagazineTitleDto> magazineTitles;
        magazineTitles = magazines.stream().map( //dto 생성시 modelmapper를 통한 방식으로 통일하기
                (magazine) -> MagazineTitleDto.builder().id(magazine.getId()).title(magazine.getTitle()).imageUrl(null).build())
                .collect(Collectors.toList());
        return magazineTitles;
    }

    @Override
    public List<CellDto> getMagazineCells(Long id) {
        Magazine magazine = magazineRepository.findById(id).orElseThrow(()-> new MagazineNotFoundException("존재하지 않는 매거진입니다."));
        List<Cell> cells = cellRepository.findCellsByMagazine(magazine);
        List<CellDto> cellDtos = cells.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                .collect(Collectors.toList());
        return cellDtos;
    }

}
