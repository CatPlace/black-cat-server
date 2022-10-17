package com.spring.blackcat.likes.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesUserResDto {
    private Long likesId;
    private String userId;
    private String userName;
    private LocalDateTime createdDate;

    public LikesUserResDto(Long likesId, String userId, String name, LocalDateTime createdDate) {
        this.likesId = likesId;
        this.userId = userId;
        this.userName = name;
        this.createdDate = createdDate;
    }
}
