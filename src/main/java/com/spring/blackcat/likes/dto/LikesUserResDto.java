package com.spring.blackcat.likes.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesUserResDto {

    private Long likesId;
    private Long userId;
    private String nickname;
    private String imageUrl;
    private LocalDateTime createdDate;

    public LikesUserResDto(Long likesId, Long userId, String nickname, String imageUrl, LocalDateTime createdDate) {
        this.likesId = likesId;
        this.userId = userId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
    }
}
