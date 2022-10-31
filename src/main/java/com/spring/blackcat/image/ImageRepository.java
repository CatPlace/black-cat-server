package com.spring.blackcat.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    
    List<Image> findByPostId(Long postId);
}
