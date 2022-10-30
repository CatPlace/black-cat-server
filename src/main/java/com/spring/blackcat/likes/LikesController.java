package com.spring.blackcat.likes;

import com.spring.blackcat.common.security.interceptor.UserId;
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

@RequestMapping("/api/v1/likes")
@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // TODO: userId 제거
    @GetMapping("/posts/{postId}")
    public LikesStatusResDto isLikedThisPost(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return likesService.isLikedThisPost(postId, userId);
    }

    // TODO: userId 제거
    @PostMapping("/posts/{postId}")
    public LikesStatusResDto likesOn(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return likesService.likesOn(postId, userId);
    }

    // TODO: userId 제거
    @DeleteMapping("/posts/{postId}")
    public LikesStatusResDto likesOff(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return likesService.likesOff(postId, userId);
    }

    @GetMapping("/users")
    public Page<LikesUserResDto> likesUsers(
            @PageableDefault(size = 50) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long postId) {
        return likesService.findLikesUsersByPostId(pageable, postId);
    }

    // TODO: userId 제거
    @GetMapping("/posts")
    public Page<LikesPostResDto> likesPosts(
            @PageableDefault(size = 50) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @UserId Long userId,
            @RequestParam(required = false) String postType) {
        return likesService.findLikesPostsByUserIdAndPostType(pageable, userId, postType);
    }
}
