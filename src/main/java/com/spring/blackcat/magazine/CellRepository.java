package com.spring.blackcat.magazine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, Long> {
    List<Cell> findCellsByMagazine(Magazine magazine);
}
