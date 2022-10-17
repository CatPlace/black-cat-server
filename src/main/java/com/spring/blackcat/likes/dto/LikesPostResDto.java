package com.spring.blackcat.likes.dto;

import com.spring.blackcat.common.code.PostType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesPostResDto {

    // TODO: Post 엔티티에 Title, Image 공통 속성으로 두고 함께 반환
    private Long likesId;
    private Long postId;
    private PostType postType;
    private LocalDateTime createdDate;

    public LikesPostResDto(Long likesId, Long postId, PostType postType, LocalDateTime createdDate) {
        this.likesId = likesId;
        this.postId = postId;
        this.postType = postType;
        this.createdDate = createdDate;
    }
}
