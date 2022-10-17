package com.spring.blackcat.tattoo;

import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.CategoryNotFoundException;
import com.spring.blackcat.common.exception.custom.TattooNotFoundException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.dto.CreateTattooDto;
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
    private final CategoryRepository categoryRepository;

    @Override
    public Page<GetTattoosResDto> getAllTattoos(Pageable pageable, String userId) {
        return this.tattooRepository.findAll(pageable).map(tattoo -> {
            boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
            String tattooistName = this.getPostingTattooistName(tattoo);
            String tattooistAddress = this.getTattooistAddress(tattoo);

            GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(),
                    tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked, tattooistAddress);

            return getTattoosResDto;
        });
    }

    @Override
    public Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, String userId, Long categoryId) {
        return this.tattooRepository.findByCategoryId(pageable, categoryId).map(tattoo -> {
            boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
            String tattooistName = this.getPostingTattooistName(tattoo);
            String tattooistAddress = this.getTattooistAddress(tattoo);

            GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(),
                    tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked, tattooistAddress);

            return getTattoosResDto;
        });
    }

    @Override
    public Long createTattoo(String userId, CreateTattooDto createTattooDto) {
        Category category = this.categoryRepository.findById(createTattooDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다.", ErrorInfo.CATEGORY_NOT_FOUND_EXCEPTION));
        Tattoo tattoo = new Tattoo(
                createTattooDto.getTitle(), createTattooDto.getDescription(), createTattooDto.getPrice(), category, createTattooDto.getTattooType(), userId, userId);

        Tattoo createdTattoo = this.tattooRepository.save(tattoo);

        return createdTattoo.getId();
    }

    private boolean isUserLikedTattoo(Long postId, String userId) {
        List<Likes> likes = this.postRepository.findById(postId).get().getLikes()
                .stream()
                .filter(like -> like.getUser().getId().equals(userId)).collect(Collectors.toList());

        return likes.size() > 0 ? true : false;
    }

    //수정필요
    private String getPostingTattooistName(Tattoo tattoo) {
        return this.tattooRepository.findById(tattoo.getId())
                .orElseThrow(() -> new TattooNotFoundException("존재하지 않는 타투 입니다.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION)).getRegisterId();
    }

    private String getTattooistAddress(Tattoo tattoo) {
        return this.userRepository.findById(tattoo.getRegisterId())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION)).getAddress().getSido();
    }
}
