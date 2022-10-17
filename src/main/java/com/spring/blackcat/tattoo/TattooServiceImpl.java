package com.spring.blackcat.tattoo;

import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.PostNotFoundException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TattooServiceImpl implements TattooService {
    private final TattooRepository tattooRepository;
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Override
    public Page<GetTattoosResDto> getAllTattoos(Pageable pageable, String userId) {
        return this.tattooRepository.findAll(pageable).map(tattoo -> {
            boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
            String tattooistName = this.getPostingTattooistName(tattoo.getId());
            String tattooistAddress = this.getTattooistAddress(tattoo.getId());

            GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(),
                    tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked, tattooistAddress);

            return getTattoosResDto;
        });
    }

    @Override
    public Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, String userId, Long categoryId) {
        return this.tattooRepository.findByCategoryId(pageable, categoryId).map(tattoo -> {
            boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
            String tattooistName = this.getPostingTattooistName(tattoo.getId());
            String tattooistAddress = this.getTattooistAddress(tattoo.getId());

            GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(),
                    tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked, tattooistAddress);

            return getTattoosResDto;
        });
    }

    private boolean isUserLikedTattoo(Long postId, String userId) {
        List<Likes> likes = this.postRepository.findById(postId).get().getLikes()
                .stream()
                .filter(like -> like.getUser().getId().equals(userId)).collect(Collectors.toList());

        return likes.size() > 0 ? true : false;
    }

    //수정필요
    private String getPostingTattooistName(Long postId) {
        return this.postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시물 입니다.", ErrorInfo.POST_NOT_FOUND_EXCEPTION)).getRegisterId();
    }

    private String getTattooistAddress(Long postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시물 입니다.", ErrorInfo.POST_NOT_FOUND_EXCEPTION));

        return this.userRepository.findById(post.getRegisterId())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION)).getAddress().getSido();
    }
}
