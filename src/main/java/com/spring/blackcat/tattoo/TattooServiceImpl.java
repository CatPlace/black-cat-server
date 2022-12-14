package com.spring.blackcat.tattoo;

import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.common.code.TattooType;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.CategoryNotFoundException;
import com.spring.blackcat.common.exception.custom.TattooNotFoundException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.image.ImageService;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.dto.CreateTattooDto;
import com.spring.blackcat.tattoo.dto.CreateTattooResDto;
import com.spring.blackcat.tattoo.dto.GetTattooResDto;
import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TattooServiceImpl implements TattooService {
    @Value("${file.path}")
    private String imageSavePath;
    private final TattooRepository tattooRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final AddressRepository addressRepository;

    private final ImageService imageService;

    @Override
    public Page<GetTattoosResDto> getAllTattoos(Pageable pageable, Long userId, String tattooType, Long addressId) {
        return this.tattooRepository.findTattoos(pageable, tattooType, addressId).map(tattoo -> {
            return this.convertToGetTattoosRes(tattoo, userId);
        });
    }

    @Override
    public Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, Long userId, Long categoryId, String tattooType, Long addressId) {
        isExistCategory(categoryId);

        return this.tattooRepository.findTattoosByCategoryId(pageable, categoryId, tattooType, addressId).map(tattoo -> {
            return this.convertToGetTattoosRes(tattoo, userId);
        });
    }

    private void isExistCategory(Long categoryId) {
        this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("???????????? ?????? ???????????? ?????????.", ErrorInfo.CATEGORY_NOT_FOUND_EXCEPTION));
    }

    @Override
    public GetTattooResDto getTattooById(Long tattooId, Long userId) {
        Tattoo tattoo = this.tattooRepository.findById(tattooId)
                .orElseThrow(() -> new TattooNotFoundException("???????????? ?????? ?????? ?????????.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION));

        return this.convertToGetTattooRes(tattoo, userId);
    }

    @Override
    @Transactional
    public CreateTattooResDto createTattoo(Long userId, CreateTattooDto createTattooDto, List<MultipartFile> images) {
        Category category = this.categoryRepository.findById(createTattooDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("???????????? ?????? ???????????? ?????????.", ErrorInfo.CATEGORY_NOT_FOUND_EXCEPTION));

        User user = findUserById(userId);

        Tattoo tattoo = new Tattoo(
                createTattooDto.getTitle(), createTattooDto.getDescription(), createTattooDto.getPrice(), category, createTattooDto.getTattooType(), user);

        Tattoo createdTattoo = this.tattooRepository.save(tattoo);

        List<String> imageUrls = this.imageService.saveImage(tattoo, images);

        CreateTattooResDto createTattooResDto = new CreateTattooResDto(createdTattoo.getId(), imageUrls);

        return createTattooResDto;
    }

    private User findUserById(Long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("???????????? ?????? ????????? ?????????.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));
    }

    private GetTattoosResDto convertToGetTattoosRes(Tattoo tattoo, Long userId) {
        System.out.println(tattoo);
        boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);

        String tattooistName = this.getPostingTattooistName(tattoo);
        String tattooistAddress = this.getTattooistAddress(tattoo);
        List<String> imageUrls = this.imageService.getImageUrls(tattoo.getId());
        TattooType tattooType = tattoo.getTattooType();

        GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(),
                tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked, tattooistAddress, imageUrls, tattooType);

        return getTattoosResDto;
    }

    private GetTattooResDto convertToGetTattooRes(Tattoo tattoo, Long userId) {
        GetTattoosResDto getTattoosResDto = this.convertToGetTattoosRes(tattoo, userId);
        int likeCount = this.getLikeCount(getTattoosResDto.getId());

        GetTattooResDto getTattooResDto = new GetTattooResDto(
                getTattoosResDto.getId(), getTattoosResDto.getPrice(), getTattoosResDto.getTattooistName(), getTattoosResDto.getDescription(),
                getTattoosResDto.isLiked(), getTattoosResDto.getAddress(), getTattoosResDto.getImageUrls(), getTattoosResDto.getTattooType(), likeCount);

        return getTattooResDto;
    }

    private boolean isUserLikedTattoo(Long postId, Long userId) {
        List<Likes> likes = this.postRepository.findById(postId).get().getLikes()
                .stream()
                .filter(like -> like.getUser().getId().equals(userId)).collect(Collectors.toList());

        return likes.size() > 0 ? true : false;
    }

    private int getLikeCount(Long tattooId) {
        List<Likes> likes = this.postRepository.findById(tattooId).get().getLikes();

        return likes.size();
    }

    private String getPostingTattooistName(Tattoo tattoo) {
        Long userId = this.tattooRepository.findById(tattoo.getId())
                .orElseThrow(() -> new TattooNotFoundException("???????????? ?????? ?????? ?????????.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION)).getRegister().getId();

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("???????????? ?????? ????????? ?????????.", ErrorInfo.USER_NOT_FOUND_EXCEPTION)).getName();
    }

    private String getTattooistAddress(Tattoo tattoo) {
        return this.userRepository.findById(tattoo.getRegister().getId())
                .orElseThrow(() -> new UserNotFoundException("???????????? ?????? ????????? ?????????.", ErrorInfo.USER_NOT_FOUND_EXCEPTION)).getAddress().getSido();
    }
}
