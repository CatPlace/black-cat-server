package com.spring.blackcat.tattoo;

import com.spring.blackcat.common.code.TattooType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TattooRepositoryCustom {

    Page<Tattoo> findTattoos(Pageable pageable, List<TattooType> tattooTypes, List<Long> addressIds);

    Page<Tattoo> findTattoosByCategoryId(Pageable pageable, Long categoryId, List<TattooType> tattooTypes, List<Long> addressIds);
}
