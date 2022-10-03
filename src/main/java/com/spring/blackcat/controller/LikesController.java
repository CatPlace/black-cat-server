package com.spring.blackcat.controller;

import com.spring.blackcat.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/likes/{post_id}/{user_id}")
    public boolean searchLike(
            @PathVariable("post_id") Long postId,
            @PathVariable("user_id") String userId) {
        return likesService.isLikedThisPost(postId, userId);
    }

    @PostMapping("/likes/{post_id}/{user_id}")
    public void likeOn(
            @PathVariable("post_id") Long postId,
            @PathVariable("user_id") String userId) {
        likesService.likesOn(postId, userId);
    }

    @DeleteMapping("/likes/{post_id}/{user_id}")
    public void likeOff(
            @PathVariable("post_id") Long postId,
            @PathVariable("user_id") String userId) {
        likesService.likesOff(postId, userId);
    }
}
