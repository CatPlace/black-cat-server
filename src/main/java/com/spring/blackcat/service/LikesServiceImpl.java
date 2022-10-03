package com.spring.blackcat.service;

import com.spring.blackcat.domain.User;
import com.spring.blackcat.repository.PostRepository;
import com.spring.blackcat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 특정 게시물 좋아요 조회
     */
    @Override
    public boolean isLikedThisPost(Long postId, String userId) {
        User user = userRepository.findById(userId).get();
        return user.getLikePosts().stream().anyMatch(post -> post.getId().equals(postId));
    }

    /**
     * 특정 게시물 좋아요 활성화
     */
    @Override
    @Transactional
    public void likesOn(Long postId, String userId) {
        User user = userRepository.findById(userId).get();
        user.getLikePosts().add(postRepository.findById(postId).get());
    }

    /**
     * 특정 게시물 좋아요 비활성화
     */
    @Override
    @Transactional
    public void likesOff(Long postId, String userId) {
        User user = userRepository.findById(userId).get();
        user.getLikePosts().remove(postRepository.findById(postId).get());
    }
}
