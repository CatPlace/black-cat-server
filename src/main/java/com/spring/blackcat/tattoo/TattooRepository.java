package com.spring.blackcat.tattoo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TattooRepository extends JpaRepository<Tattoo, Long> {
    List<Tattoo> findByCategoryId(Pageable pageable, Long categoryId);
}
