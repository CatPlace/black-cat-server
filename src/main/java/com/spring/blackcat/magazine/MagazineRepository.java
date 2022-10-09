package com.spring.blackcat.magazine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Optional<Magazine> findById(Long id);
}
