package com.spring.blackcat.likes.dto;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.post.Post;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesPostResDto {
    private Long id;
    private PostType postTypeCd;

    public LikesPostResDto(Post post) {
        this.id = post.getId();
        this.postTypeCd = post.getPostTypeCd();
    }
}
