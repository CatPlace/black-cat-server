package com.spring.blackcat.tattoo;

import com.spring.blackcat.common.code.TattooType;
import com.spring.blackcat.tattoo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TattooService {
    Page<GetTattoosResDto> getAllTattoos(Pageable pageable, List<TattooType> tattooTypes, List<Long> addressIds);

    Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, Long categoryId, List<TattooType> tattooTypes, List<Long> addressIds);

    CreateTattooResDto createTattoo(Long userId, CreateTattooDto createTattooDto, List<MultipartFile> images);

    GetTattooResDto getTattooById(Long tattooId);

    DeleteTattoosResDto deleteTattoos(DeleteTattoosReqDto deleteTattoosReqDto);

    Page<GetTattoosByUserIdResDto> getTattoosByUserId(Pageable pageable, Long userId);

    CreateTattooResDto updateTattoo(Long userId, Long tattooId, UpdateTattooReqDto updateTattooReqDto, List<MultipartFile> images);
}
