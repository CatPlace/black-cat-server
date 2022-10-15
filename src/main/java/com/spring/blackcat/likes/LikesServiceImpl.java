package com.spring.blackcat.likes;

import com.spring.blackcat.likes.dto.LikesPostResDto;
import com.spring.blackcat.likes.dto.LikesStatusResDto;
import com.spring.blackcat.likes.dto.LikesUserResDto;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<LikesUserResDto> findUsersByPostId(Long postId) {
        List<Likes> likesList = likesRepository.findByPostId(postId);
        return likesList.stream().map(likes -> new LikesUserResDto(likes.getUser())).collect(Collectors.toList());
    }

    /**
     * 특정 유저의 좋아요한 게시물 리스트 조회
     */
    @Override
    @Transactional
    public List<LikesPostResDto> findPostsByUserId(String userId) {
        List<Likes> likesList = likesRepository.findByUserId(userId);
        return likesList.stream().map(likes -> new LikesPostResDto(likes.getPost())).collect(Collectors.toList());
    }
}
