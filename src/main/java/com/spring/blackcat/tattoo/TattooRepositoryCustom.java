package com.spring.blackcat.tattoo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TattooRepositoryCustom {

    Page<Tattoo> findTattoos(Pageable pageable, String tattooType, Long addressId);

    Page<Tattoo> findTattoosByCategoryId(Pageable pageable, Long categoryId, String tattooType, Long addressId);
}
