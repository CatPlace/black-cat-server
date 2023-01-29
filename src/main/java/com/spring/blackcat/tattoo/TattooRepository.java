package com.spring.blackcat.tattoo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TattooRepository extends JpaRepository<Tattoo, Long>, TattooRepositoryCustom {
    Page<Tattoo> findAll(Pageable pageable);

    Page<Tattoo> findByUserId(Pageable pageable, Long userId);

    Optional<Tattoo> findById(Long id);

    @Transactional
    void deleteById(Long tattooId);
}
