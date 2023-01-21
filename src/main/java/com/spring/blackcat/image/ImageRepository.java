package com.spring.blackcat.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByPostId(Long postId);

    @Transactional
    void deleteByImageUrl(String imageUrl);
}
