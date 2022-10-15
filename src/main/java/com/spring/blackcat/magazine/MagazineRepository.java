package com.spring.blackcat.magazine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Page<Magazine> findAll(Pageable pageable);
}
