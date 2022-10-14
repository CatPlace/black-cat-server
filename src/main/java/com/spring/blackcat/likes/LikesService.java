package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;

import java.util.List;

public interface LikesService {

    LikesStatusResDto isLikedThisPost(Long postId, String userId);

    LikesStatusResDto likesOn(Long postId, String userId);

    LikesStatusResDto likesOff(Long postId, String userId);

    List<LikesUserResDto> findUsersByPostId(Long postId);

    List<LikesPostResDto> findPostsByUserId(String userId);
}
