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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/posts/{postId}")
    public LikesStatusResDto isLikedThisPost(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return likesService.isLikedThisPost(postId, userId);
    }

    @PostMapping("/posts/{postId}")
    public LikesStatusResDto likesOn(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return likesService.likesOn(postId, userId);
    }

    @DeleteMapping("/posts/{postId}")
    public LikesStatusResDto likesOff(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return likesService.likesOff(postId, userId);
    }

    @GetMapping("posts/{postId}/users")
    public Page<LikesUserResDto> likesUsers(
            @PageableDefault(size = 50) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long postId) {
        return likesService.findLikesUsersByPostId(pageable, postId);
    }

    @GetMapping("/posts")
    public Page<LikesPostResDto> likesPosts(
            @PageableDefault(size = 50) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @UserId Long userId,
            @RequestParam(required = false) String postType) {
        return likesService.findLikesPostsByUserIdAndPostType(pageable, userId, postType);
    }
}
