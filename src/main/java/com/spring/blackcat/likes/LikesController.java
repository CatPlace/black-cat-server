package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // TODO: userId 제거
    @GetMapping("/users/{userId}/likes/posts/{postId}")
    public LikesStatusResDto isLikedThisPost(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.isLikedThisPost(postId, userId);
    }

    // TODO: userId 제거
    @PostMapping("/users/{userId}/likes/posts/{postId}")
    public LikesStatusResDto likesOn(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.likesOn(postId, userId);
    }

    // TODO: userId 제거
    @DeleteMapping("/users/{userId}/likes/posts/{postId}")
    public LikesStatusResDto likesOff(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") String userId) {
        return likesService.likesOff(postId, userId);
    }

    @GetMapping("posts/{postId}/likes/users")
    public Page<LikesUserResDto> likesUsers(
            @PageableDefault(size = 50) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long postId) {
        return likesService.findUsersByPostId(pageable, postId);
    }

    // TODO: userId 제거
    @GetMapping("users/{userId}/likes/posts")
    public Page<LikesPostResDto> likesPosts(
            @PageableDefault(size = 50) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String userId,
            @RequestParam(required = false) String postType) {
        return likesService.findPostByUserIdAndFilter(pageable, userId, postType);
    }
}
