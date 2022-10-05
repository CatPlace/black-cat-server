package com.spring.blackcat.likes;

import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public LikesStatusDto isLikedThisPost(Long postId, String userId) {
        Optional<Likes> likes = likesRepository.findByPostIdAndUserId(postId, userId);
        return new LikesStatusDto(likes.isPresent());
    }

    /**
     * 특정 게시물 좋아요 활성화
     */
    @Override
    @Transactional
    public LikesStatusDto likesOn(Long postId, String userId) {
        boolean isExists = likesRepository.findByPostIdAndUserId(postId, userId).isPresent();
        if (isExists) {
            return new LikesStatusDto(true);
        }
        Post post = postRepository.findById(postId).get();
        User user = userRepository.findById(userId).get();
        Likes likes = new Likes(post, user, post.getPostTypeCd(), userId, userId);
        likesRepository.save(likes);
        return new LikesStatusDto(true);
    }

    /**
     * 특정 게시물 좋아요 비활성화
     */
    @Override
    @Transactional
    public LikesStatusDto likesOff(Long postId, String userId) {
        Likes likes = likesRepository.findByPostIdAndUserId(postId, userId).get();
        likesRepository.delete(likes);
        return new LikesStatusDto(false);
    }
}
