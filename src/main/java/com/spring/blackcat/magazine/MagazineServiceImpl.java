package com.spring.blackcat.magazine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MagazineServiceImpl implements MagazineService{

    private final MagazineRepository magazineRepository;


    @Override
    public List<MagazineTitleDto> findAll() {
        List<Magazine> magazines = magazineRepository.findAll();
        List<MagazineTitleDto> magazineTitles = new ArrayList<>();
        magazineTitles = magazines.stream().map((magazine)->MagazineTitleDto.builder().id(magazine.getId()).title(magazine.getTitle()).imageUrl(null).build()).collect(Collectors.toList());
        return magazineTitles;
    }

    @Override
    public Magazine findById(Long id) {
        return magazineRepository.findById(id).orElseThrow(()->new MagazineNotFoundException("존재하지 않는 매거진입니다."));
    }
}
