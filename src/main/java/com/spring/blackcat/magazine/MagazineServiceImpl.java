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

    @Override
    public List<MagazineTitleDto> findAll() {
        List<Magazine> magazines = magazineRepository.findAll();
        List<MagazineTitleDto> magazineTitles;
        magazineTitles = magazines.stream().map(
                (magazine)->MagazineTitleDto.builder().id(magazine.getId()).title(magazine.getTitle()).imageUrl(null).build())
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
