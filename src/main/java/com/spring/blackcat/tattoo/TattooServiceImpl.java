package com.spring.blackcat.tattoo;

import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.categoryPost.CategoryPost;
import com.spring.blackcat.categoryPost.CategoryPostRepository;
import com.spring.blackcat.common.code.ImageType;
import com.spring.blackcat.common.code.TattooType;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.CategoryNotFoundException;
import com.spring.blackcat.common.exception.custom.TattooNotFoundException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.image.ImageService;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.dto.*;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    private final CategoryPostRepository categoryPostRepository;

    private final ImageService imageService;

    @Override
    public Page<GetTattoosResDto> getAllTattoos(Pageable pageable, List<TattooType> tattooTypes, List<Long> addressIds) {
        return this.tattooRepository.findTattoos(pageable, tattooTypes, addressIds).map(tattoo -> {
            return this.convertToGetTattoosRes(tattoo);
        });
    }

    @Override
    public Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, Long categoryId, List<TattooType> tattooTypes, List<Long> addressIds) {
        isExistCategory(categoryId);

        return this.tattooRepository.findTattoosByCategoryId(pageable, categoryId, tattooTypes, addressIds).map(tattoo -> {
            return this.convertToGetTattoosRes(tattoo);
        });
    }

    private void isExistCategory(Long categoryId) {
        this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다.", ErrorInfo.CATEGORY_NOT_FOUND_EXCEPTION));
    }

    @Override
    public GetTattooResDto getTattooById(Long tattooId, Long userId) {
        Tattoo tattoo = this.tattooRepository.findById(tattooId)
                .orElseThrow(() -> new TattooNotFoundException("존재하지 않는 타투 입니다.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION));

        return this.convertToGetTattooRes(tattoo, userId);
    }

    @Override
    public DeleteTattoosResDto deleteTattoos(DeleteTattoosReqDto deleteTattoosReqDto) {
        for (Long tattooId : deleteTattoosReqDto.getTattooIds()) this.tattooRepository.deleteById(tattooId);

        DeleteTattoosResDto deleteTattoosResDto = new DeleteTattoosResDto(deleteTattoosReqDto.getTattooIds());

        return deleteTattoosResDto;
    }

    @Override
    public Page<GetTattoosByUserIdResDto> getTattoosByUserId(Pageable pageable, Long tattooistId) {
        return this.tattooRepository.findByUserId(pageable, tattooistId)
                .map(tattoo -> {
                    Long tattooId = tattoo.getId();
                    List<String> imageUrls = this.imageService.getImageUrls(ImageType.POST, tattooId);
                    String imageUrl = imageUrls.isEmpty() ? "" : imageUrls.get(0);

                    return new GetTattoosByUserIdResDto(tattooId, imageUrl);
                });
    }

    @Override
    @Transactional
    public CreateTattooResDto createTattoo(Long userId, CreateTattooDto createTattooDto, List<MultipartFile> images) {
        User user = findUserById(userId);

        Tattoo tattoo = new Tattoo(
                createTattooDto.getTitle(), createTattooDto.getDescription(), createTattooDto.getPrice(), createTattooDto.getTattooType(), user);

        //타투 저장
        Tattoo createdTattoo = this.tattooRepository.save(tattoo);

        //카테고리 <-> 포스트 관계 테이블에 저장
        for (Long categoryId : createTattooDto.getCategoryIds()) {
            createCategoryPost(createdTattoo, categoryId);
        }

        //이미지 저장
        List<String> imageUrls = this.imageService.saveImage(ImageType.POST, tattoo.getId(), images);

        CreateTattooResDto createTattooResDto = new CreateTattooResDto(createdTattoo.getId(), imageUrls);

        return createTattooResDto;
    }

    @Override
    @Transactional
    public CreateTattooResDto updateTattoo(Long userId, Long tattooId, UpdateTattooReqDto updateTattooReqDto, List<MultipartFile> images) {
        Tattoo tattoo = this.tattooRepository.findById(tattooId)
                .orElseThrow(() -> new TattooNotFoundException("존재하지 않는 타투 입니다.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION));

        tattoo.updateTattoo(updateTattooReqDto.getTitle(), updateTattooReqDto.getDescription(),
                updateTattooReqDto.getPrice(), updateTattooReqDto.getTattooType());

        //카테고리 <-> 포스트 관계 테이블에 저장
        this.categoryPostRepository.deleteByPostId(tattooId);
        for (Long categoryId : updateTattooReqDto.getCategoryIds()) {
            createCategoryPost(tattoo, categoryId);
        }

        User user = findUserById(userId);

        List<String> imageUrls = updateImages(tattooId, updateTattooReqDto, images, user);

        CreateTattooResDto createTattooResDto = new CreateTattooResDto(tattooId, imageUrls);

        return createTattooResDto;
    }

    private void createCategoryPost(Tattoo tattoo, Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다.", ErrorInfo.CATEGORY_NOT_FOUND_EXCEPTION));
        Post post = this.postRepository.findById(tattoo.getId())
                .orElseThrow(() -> new TattooNotFoundException("존재하지 않는 타투입니다.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION));

        CategoryPost categoryPost = new CategoryPost(post, category);

        tattoo.addCategoryPost(categoryPost);
        categoryPostRepository.save(categoryPost);
    }

    private List<String> updateImages(Long tattooId, UpdateTattooReqDto updateTattooReqDto, List<MultipartFile> images, User user) {
        deleteImages(updateTattooReqDto.getDeleteImageUrls());
        if (images != null) {
            this.imageService.saveImage(ImageType.POST, user.getId(), images);
        }
        List<String> imageUrls = this.imageService.getImageUrls(ImageType.POST, tattooId);
        return imageUrls;
    }

    private void deleteImages(List<String> imageUrls) {
        imageUrls.forEach(imageUrl -> this.imageService.deleteImage(imageUrl));
    }

    private User findUserById(Long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));
    }

    private GetTattoosResDto convertToGetTattoosRes(Tattoo tattoo) {

        Long tattooistId = tattoo.getUser().getId();
        String title = tattoo.getTitle();
        String tattooistName = this.getPostingTattooistName(tattoo);
        String tattooistAddress = this.getTattooistAddress(tattoo);
        List<String> imageUrls = this.imageService.getImageUrls(ImageType.POST, tattoo.getId());
        TattooType tattooType = tattoo.getTattooType();
        List<Long> categoryIds = getCategoryIds(this.categoryPostRepository.findByPostId(tattoo.getId()));


        GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(), title,
                tattoo.getPrice(), tattooistId, tattooistName, tattoo.getDescription(), tattooistAddress, imageUrls, tattooType, categoryIds);

        return getTattoosResDto;
    }

    private List<Long> getCategoryIds(List<CategoryPost> categoryPosts) {
        List<Long> categoryIds = new ArrayList<>();

        for (CategoryPost categoryPost : categoryPosts) {
            categoryIds.add(categoryPost.getCategory().getId());
        }

        return categoryIds;
    }

    private GetTattooResDto convertToGetTattooRes(Tattoo tattoo, Long userId) {
        boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
        GetTattoosResDto getTattoosResDto = this.convertToGetTattoosRes(tattoo);
        int likeCount = this.getLikeCount(getTattoosResDto.getId());
        List<String> profileImageUrls = this.imageService.getImageUrls(ImageType.USER, tattoo.getUser().getId());

        GetTattooResDto getTattooResDto = new GetTattooResDto(
                getTattoosResDto.getId(), getTattoosResDto.getTitle(), getTattoosResDto.getPrice(), getTattoosResDto.getTattooistId(),
                getTattoosResDto.getTattooistName(), getTattoosResDto.getDescription(), isLiked,
                getTattoosResDto.getAddress(), getTattoosResDto.getImageUrls(), getTattoosResDto.getTattooType(),
                getTattoosResDto.getCategoryIds(), likeCount, tattoo.getCreatedDate(), profileImageUrls);

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
                .orElseThrow(() -> new TattooNotFoundException("존재하지 않는 타투 입니다.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION)).getUser().getId();

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION)).getName();
    }

    private String getTattooistAddress(Tattoo tattoo) {
        return this.userRepository.findById(tattoo.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION)).getAddress().getSido();
    }
}
