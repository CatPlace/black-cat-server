package com.spring.blackcat.likes;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/likes/{postId}/{userId}")
    public LikesStatusDto isLikedThisPost(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.isLikedThisPost(postId, userId);
    }

    @PostMapping("/likes/{postId}/{userId}")
    public LikesStatusDto likesOn(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.likesOn(postId, userId);
    }

    @DeleteMapping("/likes/{postId}/{userId}")
    public LikesStatusDto likesOff(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.likesOff(postId, userId);
    }
}
