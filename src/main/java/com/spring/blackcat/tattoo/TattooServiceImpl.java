package com.spring.blackcat.tattoo;

import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TattooServiceImpl implements TattooService {
    private final TattooRepository tattooRepository;
    private final PostRepository postRepository;

    @Override
    public List<GetTattoosResDto> getAllTattoos(Pageable pageable, String userId) {
        return this.tattooRepository.findAll(pageable).stream().map(tattoo -> {
            boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
            String tattooistName = this.getPostingTattooistName(tattoo.getId());

            GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(), tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked);

            return getTattoosResDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, String userId, Long categoryId) {
        return this.tattooRepository.findByCategoryId(pageable, categoryId).stream().map(tattoo -> {
            boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
            String tattooistName = this.getPostingTattooistName(tattoo.getId());

            GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(), tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked);

            return getTattoosResDto;
        }).collect(Collectors.toList());
    }

    private boolean isUserLikedTattoo(Long postId, String userId) {
        List<Likes> likes = this.postRepository.findById(postId).get().getLikes()
                .stream()
                .filter(like -> like.getUser().getId().equals(userId)).collect(Collectors.toList());

        return likes.size() > 0 ? true : false;
    }

    //수정필요
    private String getPostingTattooistName(Long postId) {
        return this.postRepository.findById(postId).get().getRegisterId();
    }
}
