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
    Page<GetTattoosResDto> getAllTattoos(Pageable pageable, String userId);

    Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, String userId, Long categoryId);

    CreateTattooResDto createTattoo(String userId, CreateTattooDto createTattooDto, List<MultipartFile> images);

    GetTattooResDto getTattooById(Long tattooId, String userId);
}
