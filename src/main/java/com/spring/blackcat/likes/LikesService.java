package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikesService {

    LikesStatusResDto isLikedThisPost(Long postId, Long userId);

    LikesStatusResDto likesOn(Long postId, Long userId);

    LikesStatusResDto likesOff(Long postId, Long userId);

    Page<LikesUserResDto> findLikesUsersByPostId(Pageable pageable, Long postId);

    Page<LikesPostResDto> findLikesPostsByUserIdAndPostType(Pageable pageable, Long userId, String postType);
}
