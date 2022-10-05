package com.spring.blackcat.tattoo;

import com.spring.blackcat.likes.LikesRepository;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.dto.GetAllTattoosResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TattooServiceImpl implements TattooService {
    private final TattooRepository tattooRepository;
    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    @Override
    public List<GetAllTattoosResDto> getAllTattoos(Pageable pageable, String userId) {
        return this.tattooRepository.findAll(pageable).stream().map(tattoo -> {
            boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
            String tattooistName = this.getPostingTattooistName(tattoo.getId());

            GetAllTattoosResDto getAllTattoosResDto = new GetAllTattoosResDto(tattoo.getId(), tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked);

            return getAllTattoosResDto;
        }).collect(Collectors.toList());
    }

    private boolean isUserLikedTattoo(Long postId, String userId) {
        return this.likesRepository.findByPostIdAndUserId(postId, userId).isPresent();
    }

    //수정필요
    private String getPostingTattooistName(Long postId) {
        return this.postRepository.findById(postId).get().getRegisterId();
    }
}
