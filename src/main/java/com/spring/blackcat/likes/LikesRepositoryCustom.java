package com.spring.blackcat.likes;

import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikesRepositoryCustom {
    Page<LikesUserResDto> findLikesUsersByPostId(Pageable pageable, Long postId);

    Page<LikesPostResDto> findLikesPostsByUserIdAndPostType(Pageable pageable, String Id, PostType postType);
}
