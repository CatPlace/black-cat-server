package com.spring.blackcat.likes;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/likes/{postId}/{userId}")
    public boolean searchLike(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.isLikedThisPost(postId, userId);
    }

    @PostMapping("/likes/{postId}/{userId}")
    public void likeOn(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        likesService.likesOn(postId, userId);
    }

    @DeleteMapping("/likes/{postId}/{userId}")
    public void likeOff(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        likesService.likesOff(postId, userId);
    }
}
