package com.spring.blackcat.likes.dto;

import com.spring.blackcat.common.code.PostType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesPostResDto {

    private Long likesId;
    private Long userId;
    private Long postId;
    private PostType postType;
    private String title;
    private String imageUrl;
    private LocalDateTime createdDate;

    public LikesPostResDto(Long likesId, Long userId, Long postId, PostType postType, String title, String imageUrl, LocalDateTime createdDate) {
        this.likesId = likesId;
        this.userId = userId;
        this.postId = postId;
        this.postType = postType;
        this.title = title;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
    }
}
