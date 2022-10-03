package com.spring.blackcat.likes;

public interface LikesService {

    public boolean isLikedThisPost(Long postId, String userId);

    public void likesOn(Long postId, String userId);

    public void likesOff(Long postId, String userId);
}
