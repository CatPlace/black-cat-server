package com.spring.blackcat.tattoo;

import com.spring.blackcat.tattoo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TattooService {
    Page<GetTattoosResDto> getAllTattoos(Pageable pageable, Long userId, String tattooType, Long addressId);

    Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, Long userId, Long categoryId, String tattooType, Long addressId);

    CreateTattooResDto createTattoo(Long userId, CreateTattooDto createTattooDto, List<MultipartFile> images);

    GetTattooResDto getTattooById(Long tattooId, Long userId);

    DeleteTattoosResDto deleteTattoos(DeleteTattoosReqDto deleteTattoosReqDto);

    Page<GetTattoosByUserIdResDto> getTattoosByUserId(Pageable pageable, Long userId);
}
