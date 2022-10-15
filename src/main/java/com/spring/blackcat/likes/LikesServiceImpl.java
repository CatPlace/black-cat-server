package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<LikesUserResDto> findUsersByPostId(Pageable pageable, Long postId) {
        Page<Likes> likesList = likesRepository.findByPostId(pageable, postId);
        return likesList.map(likes -> new LikesUserResDto(likes.getUser()));
    }

    /**
     * 특정 유저의 좋아요한 게시물 리스트 조회
     */
    @Override
    @Transactional
    public Page<LikesPostResDto> findPostsByUserId(Pageable pageable, String userId) {
        Page<Likes> likesList = likesRepository.findByUserId(pageable, userId);
        return likesList.map(likes -> new LikesPostResDto(likes.getPost()));
    }
}
