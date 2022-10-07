package com.spring.blackcat.tattoo;

import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TattooService {
    List<GetTattoosResDto> getAllTattoos(Pageable pageable, String userId);

    List<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, String userId, Long categoryId);
}
