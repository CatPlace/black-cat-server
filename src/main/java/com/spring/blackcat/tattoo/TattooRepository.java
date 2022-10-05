package com.spring.blackcat.tattoo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TattooRepository extends JpaRepository<Tattoo, Long> {
    Optional<Tattoo> findByCategoryId(Long categoryId);
}
