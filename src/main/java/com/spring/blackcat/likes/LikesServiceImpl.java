package com.spring.blackcat.likes;

import com.spring.blackcat.common.code.PostType;
import com.spring.blackcat.common.exception.custom.PostNotFoundException;
import com.spring.blackcat.common.exception.custom.PostTypeNotFoundException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.likes.dto.*;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.spring.blackcat.common.exception.ErrorInfo.*;

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
    public LikesStatusResDto isLikedThisPost(Long postId, Long userId) {
        Optional<Likes> likes = likesRepository.findByPostIdAndUserId(postId, userId);
        return new LikesStatusResDto(likes.isPresent());
    }

    /**
     * 특정 게시물 좋아요 활성화
     */
    @Override
    @Transactional
    public LikesStatusResDto likesOn(Long postId, Long userId) {
        boolean isExists = likesRepository.findByPostIdAndUserId(postId, userId).isPresent();
        if (!isExists) {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new UserNotFoundException("존재하지 않는 사용자입니다.", USER_NOT_FOUND_EXCEPTION));
            Post post = postRepository.findById(postId).orElseThrow(() ->
                    new PostNotFoundException("존재하지 않는 게시물입니다.", POST_NOT_FOUND_EXCEPTION));
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
    public LikesStatusResDto likesOff(Long postId, Long userId) {
        likesRepository.findByPostIdAndUserId(postId, userId).ifPresent(likesRepository::delete);
        return new LikesStatusResDto(false);
    }

    /**
     * 여러 게시물들 좋아요 활성화
     */
    @Override
    @Transactional
    public LikesResDto multipleLikesOn(List<Long> postIds, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("존재하지 않는 사용자입니다.", USER_NOT_FOUND_EXCEPTION));
        List<Post> posts = postRepository.findByIdIn(postIds);
        List<Long> existsList = likesRepository.findPostIdsByUserId(userId);
        List<Likes> likesList = new ArrayList<>();
        posts.forEach(post -> {
            if (!existsList.contains(post.getId())) {
                likesList.add(new Likes(post, user, post.getPostType()));
            }
        });
        likesRepository.saveAll(likesList);
        return new LikesResDto(postIds);
    }

    /**
     * 여러 게시물들 좋아요 비활성화
     */
    @Override
    @Transactional
    public LikesResDto multipleLikesOff(List<Long> postIds, Long userId) {
        likesRepository.deleteAll(likesRepository.findByPostIdInAndUserId(postIds, userId));
        return new LikesResDto(postIds);
    }

    /**
     * 특정 게시물의 좋아요 수 조회
     */
    @Override
    @Transactional
    public LikesCountResDto countByPostId(Long postId) {
        return new LikesCountResDto(likesRepository.countByPostId(postId));
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
    public Page<LikesPostResDto> findLikesPostsByUserIdAndPostType(Pageable pageable, Long userId, String postType) {
        PostType postTypeEnum = null;
        if (postType != null) {
            try {
                postTypeEnum = PostType.valueOf(postType.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new PostTypeNotFoundException("존재하지 않는 게시물 유형입니다.", POST_TYPE_NOT_FOUND_EXCEPTION);
            }
        }
        return likesRepository.findLikesPostsByUserIdAndPostType(pageable, userId, postTypeEnum);
    }
}
