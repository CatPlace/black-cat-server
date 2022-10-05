package com.spring.blackcat.tattoo;

import com.spring.blackcat.tattoo.dto.GetAllTattoosResDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TattooService {
    List<GetAllTattoosResDto> getAllTattoos(Pageable pageable, String userId);
}
