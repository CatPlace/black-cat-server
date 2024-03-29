package com.spring.blackcat.estimate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    Estimate findByUserId(Long userId);
}
