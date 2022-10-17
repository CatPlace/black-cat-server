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
    private Long postId;
    private PostType postType;
    private String title;
    private String imageUrl;
    private LocalDateTime createdDate;

    public LikesPostResDto(Long likesId, Long postId, String title, String imageUrl, PostType postType, LocalDateTime createdDate) {
        this.likesId = likesId;
        this.postId = postId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.postType = postType;
        this.createdDate = createdDate;
    }
}
