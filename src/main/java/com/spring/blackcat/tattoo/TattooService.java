package com.spring.blackcat.tattoo;

import com.spring.blackcat.tattoo.dto.CreateTattooDto;
import com.spring.blackcat.tattoo.dto.CreateTattooResDto;
import com.spring.blackcat.tattoo.dto.GetTattooResDto;
import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TattooService {
    Page<GetTattoosResDto> getAllTattoos(Pageable pageable, Long userId, String tattooType, Long addressId);

    Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, Long userId, Long categoryId, String tattooType, Long addressId);

    CreateTattooResDto createTattoo(Long userId, CreateTattooDto createTattooDto, List<MultipartFile> images);

    GetTattooResDto getTattooById(Long tattooId, Long userId);
}
