package com.spring.blackcat.tattoo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TattooRepository extends JpaRepository<Tattoo, Long>, TattooRepositoryCustom {
    Page<Tattoo> findAll(Pageable pageable);

    Page<Tattoo> findByUserId(Pageable pageable, Long userId);

    @Transactional
    void deleteById(Long tattooId);
}
