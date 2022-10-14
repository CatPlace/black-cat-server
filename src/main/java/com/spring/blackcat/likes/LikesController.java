package com.spring.blackcat.likes;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // TODO: userId 제거
    @GetMapping("/user/{userId}/likes/post/{postId}")
    public LikesStatusDto isLikedThisPost(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.isLikedThisPost(postId, userId);
    }

    // TODO: userId 제거
    @PostMapping("/user/{userId}/likes/post/{postId}")
    public LikesStatusDto likesOn(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.likesOn(postId, userId);
    }

    // TODO: userId 제거
    @DeleteMapping("/user/{userId}/likes/post/{postId}")
    public LikesStatusDto likesOff(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.likesOff(postId, userId);
    }
}
