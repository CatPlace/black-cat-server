package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikesService {

    LikesStatusResDto isLikedThisPost(Long postId, String userId);

    LikesStatusResDto likesOn(Long postId, String userId);

    LikesStatusResDto likesOff(Long postId, String userId);

    Page<LikesUserResDto> findUsersByPostId(Pageable pageable, Long postId);

    Page<LikesPostResDto> findPostByUserIdAndFilter(Pageable pageable, String userId, String postType);
}
