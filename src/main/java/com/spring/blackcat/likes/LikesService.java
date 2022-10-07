package com.spring.blackcat.likes;

public interface LikesService {

    LikesStatusDto isLikedThisPost(Long postId, String userId);

    LikesStatusDto likesOn(Long postId, String userId);

    LikesStatusDto likesOff(Long postId, String userId);
}
