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
    private LocalDateTime createdDate;

    public LikesUserResDto(Long likesId, Long userId, LocalDateTime createdDate) {
        this.likesId = likesId;
        this.userId = userId;
        this.createdDate = createdDate;
    }
}
