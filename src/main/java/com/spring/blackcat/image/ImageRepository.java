package com.spring.blackcat.image;

import com.spring.blackcat.common.code.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByImageTypeAndMappedId(ImageType imageType, Long mappedId);

    @Transactional
    void deleteByImageUrl(String imageUrl);
}
