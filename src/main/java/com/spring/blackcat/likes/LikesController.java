package com.spring.blackcat.likes;

import com.spring.blackcat.common.response.ResponseDto;
import com.spring.blackcat.common.response.ResponseUtil;
import com.spring.blackcat.common.security.interceptor.UserId;
import lombok.RequiredArgsConstructor;
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
    public ResponseDto isLikedThisPost(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return ResponseUtil.SUCCESS("게시물 좋아요 조회 성공", likesService.isLikedThisPost(postId, userId));
    }

    @PostMapping("/posts/{postId}")
    public ResponseDto likesOn(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return ResponseUtil.SUCCESS("게시물 좋아요 설정 성공", likesService.likesOn(postId, userId));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseDto likesOff(
            @PathVariable("postId") Long postId,
            @UserId Long userId) {
        return ResponseUtil.SUCCESS("게시물 좋아요 해제 성공", likesService.likesOff(postId, userId));
    }

    @GetMapping("posts/{postId}/users")
    public ResponseDto likesUsers(
            @PageableDefault(size = 50) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long postId) {
        return ResponseUtil.SUCCESS("게시물 좋아요한 유저 정보 조회 성공", likesService.findLikesUsersByPostId(pageable, postId));
    }

    @GetMapping("/posts")
    public ResponseDto likesPosts(
            @PageableDefault(size = 50) @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @UserId Long userId,
            @RequestParam(required = false) String postType) {
        return ResponseUtil.SUCCESS("좋아요를 설정한 게시물 정보 조회 성공", likesService.findLikesPostsByUserIdAndPostType(pageable, userId, postType));
    }
}
