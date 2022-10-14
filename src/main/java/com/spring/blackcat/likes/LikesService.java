package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.LikesStatusResDto;

public interface LikesService {

    LikesStatusResDto isLikedThisPost(Long postId, String userId);

    LikesStatusResDto likesOn(Long postId, String userId);

    LikesStatusResDto likesOff(Long postId, String userId);
}
