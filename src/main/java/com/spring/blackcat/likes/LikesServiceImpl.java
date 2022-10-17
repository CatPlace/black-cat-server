package com.spring.blackcat.likes;

import com.spring.blackcat.code.PostType;
import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 특정 게시물 좋아요 조회
     */
    @Override
    public LikesStatusResDto isLikedThisPost(Long postId, String userId) {
        Optional<Likes> likes = likesRepository.findByPostIdAndUserId(postId, userId);
        return new LikesStatusResDto(likes.isPresent());
    }

    /**
     * 특정 게시물 좋아요 활성화
     */
    @Override
    @Transactional
    public LikesStatusResDto likesOn(Long postId, String userId) {
        boolean isExists = likesRepository.findByPostIdAndUserId(postId, userId).isPresent();
        if (isExists) {
            return new LikesStatusResDto(true);
        }
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (post != null && user != null) {
            Likes likes = new Likes(post, user, post.getPostType());
            likesRepository.save(likes);
        }
        return new LikesStatusResDto(true);
    }

    /**
     * 특정 게시물 좋아요 비활성화
     */
    @Override
    @Transactional
    public LikesStatusResDto likesOff(Long postId, String userId) {
        likesRepository.findByPostIdAndUserId(postId, userId).ifPresent(likesRepository::delete);
        return new LikesStatusResDto(false);
    }

    /**
     * 특정 게시물을 좋아요한 유저 리스트 조회
     */
    @Override
    @Transactional
    public Page<LikesUserResDto> findLikesUsersByPostId(Pageable pageable, Long postId) {
        return likesRepository.findLikesUsersByPostId(pageable, postId);
    }

    /**
     * 특정 유저의 좋아요한 게시물 리스트 조회 - 게시물 타입 필터링
     */
    @Override
    @Transactional
    public Page<LikesPostResDto> findLikesPostsByUserIdAndPostType(Pageable pageable, String userId, String postType) {
        PostType postTypeEnum = null;
        if (postType != null) {
            try {
                postTypeEnum = PostType.valueOf(postType.toUpperCase());
            } catch (IllegalArgumentException e) {
                // TODO: Enum Type 에 없는 값 파라미터로 넘어오면 [ 전체조회 | 결과없음 | 오류처리 ] 상의 필요
                // 전체조회 : catch 블록 처리 안하고 null 값 그대로 전달
                // 결과없음 : return new PageImpl<>(new ArrayList<>(), pageable, 0);
                // 오류처리 : throw new NotFoundException(HttpStatus.BAD_REQUEST, "게시물 타입을 찾을 수 없습니다.");
                return new PageImpl<>(new ArrayList<>(), pageable, 0);
            }
        }
        return likesRepository.findLikesPostsByUserIdAndPostType(pageable, userId, postTypeEnum);
    }
}
