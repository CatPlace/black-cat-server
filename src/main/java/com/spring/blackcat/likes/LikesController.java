package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // TODO: userId 제거
    @GetMapping("/user/{userId}/likes/post/{postId}")
    public LikesStatusResDto isLikedThisPost(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.isLikedThisPost(postId, userId);
    }

    // TODO: userId 제거
    @PostMapping("/user/{userId}/likes/post/{postId}")
    public LikesStatusResDto likesOn(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.likesOn(postId, userId);
    }

    // TODO: userId 제거
    @DeleteMapping("/user/{userId}/likes/post/{postId}")
    public LikesStatusResDto likesOff(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.likesOff(postId, userId);
    }

    @GetMapping("post/{postId}/likes/users")
    public List<LikesUserResDto> likesUsers(@PathVariable Long postId) {
        return likesService.findUsersByPostId(postId);
    }

    // TODO: userId 제거
    @GetMapping("user/{userId}/likes/posts")
    public List<LikesPostResDto> likesPosts(@PathVariable String userId) {
        return likesService.findPostsByUserId(userId);
    }
}
