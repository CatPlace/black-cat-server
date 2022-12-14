package com.spring.blackcat.tattoo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TattooRepository extends JpaRepository<Tattoo, Long>, TattooRepositoryCustom {
    Page<Tattoo> findAll(Pageable pageable);

}
