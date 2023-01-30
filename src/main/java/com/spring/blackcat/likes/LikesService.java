package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikesService {

    LikesStatusResDto isLikedThisPost(Long postId, Long userId);

    LikesStatusResDto likesOn(Long postId, Long userId);

    LikesStatusResDto likesOff(Long postId, Long userId);

    LikesResDto multipleLikesOn(List<Long> postIds, Long userId);

    LikesResDto multipleLikesOff(List<Long> postIds, Long userId);

    LikesCountResDto countByPostId(Long postId);

    Page<LikesUserResDto> findLikesUsersByPostId(Pageable pageable, Long postId);

    Page<LikesPostResDto> findLikesPostsByUserIdAndPostType(Pageable pageable, Long userId, String postType);
}
