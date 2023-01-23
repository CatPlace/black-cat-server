package com.spring.blackcat.image;

import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.common.code.ImageType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private Long mappedId;

    public Image(String imageUrl, ImageType imageType, Long mappedId) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.mappedId = mappedId;
    }
}
