package com.spring.blackcat.likes;

public interface LikesService {

    public LikesStatusDto isLikedThisPost(Long postId, String userId);

    public LikesStatusDto likesOn(Long postId, String userId);

    public LikesStatusDto likesOff(Long postId, String userId);
}
