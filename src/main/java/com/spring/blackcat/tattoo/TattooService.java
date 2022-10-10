package com.spring.blackcat.tattoo;

import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TattooService {
    Page<GetTattoosResDto> getAllTattoos(Pageable pageable, String userId);

    Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, String userId, Long categoryId);
}
