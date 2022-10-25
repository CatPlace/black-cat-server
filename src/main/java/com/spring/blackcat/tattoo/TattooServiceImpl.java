package com.spring.blackcat.tattoo;

import com.spring.blackcat.category.Category;
import com.spring.blackcat.category.CategoryRepository;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.CategoryNotFoundException;
import com.spring.blackcat.common.exception.custom.TattooNotFoundException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.image.Image;
import com.spring.blackcat.image.ImageRepository;
import com.spring.blackcat.likes.Likes;
import com.spring.blackcat.post.Post;
import com.spring.blackcat.post.PostRepository;
import com.spring.blackcat.tattoo.dto.CreateTattooDto;
import com.spring.blackcat.tattoo.dto.CreateTattooResDto;
import com.spring.blackcat.tattoo.dto.GetTattooResDto;
import com.spring.blackcat.tattoo.dto.GetTattoosResDto;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private final ImageRepository imageRepository;

    @Override
    public Page<GetTattoosResDto> getAllTattoos(Pageable pageable, String userId) {
        return this.tattooRepository.findAll(pageable).map(tattoo -> {
            return this.convertToGetTattoosRes(tattoo, userId);
        });
    }

    @Override
    public Page<GetTattoosResDto> getTattoosByCategoryId(Pageable pageable, String userId, Long categoryId) {
        return this.tattooRepository.findByCategoryId(pageable, categoryId).map(tattoo -> {
            return this.convertToGetTattoosRes(tattoo, userId);
        });
    }

    @Override
    public GetTattooResDto getTattooById(Long tattooId, String userId) {
        Tattoo tattoo = this.tattooRepository.findById(tattooId)
                .orElseThrow(() -> new TattooNotFoundException("존재하지 않는 타투 입니다.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION));

        return this.convertToGetTattooRes(tattoo, userId);
    }

    @Override
    public CreateTattooResDto createTattoo(String userId, CreateTattooDto createTattooDto, List<MultipartFile> images) {
        Category category = this.categoryRepository.findById(createTattooDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다.", ErrorInfo.CATEGORY_NOT_FOUND_EXCEPTION));
        Tattoo tattoo = new Tattoo(
                createTattooDto.getTitle(), createTattooDto.getDescription(), createTattooDto.getPrice(), category, createTattooDto.getTattooType(), userId, userId);

        Tattoo createdTattoo = this.tattooRepository.save(tattoo);

        List<String> imageUrls = this.uploadImages(images);

        this.saveImages(createdTattoo.getId(), imageUrls);

        CreateTattooResDto createTattooResDto = new CreateTattooResDto(createdTattoo.getId(), imageUrls);

        return createTattooResDto;
    }

    private GetTattoosResDto convertToGetTattoosRes(Tattoo tattoo, String userId) {
        boolean isLiked = this.isUserLikedTattoo(tattoo.getId(), userId);
        String tattooistName = this.getPostingTattooistName(tattoo);
        String tattooistAddress = this.getTattooistAddress(tattoo);
        List<String> imageUrls = this.getImageUrls(tattoo.getId());

        GetTattoosResDto getTattoosResDto = new GetTattoosResDto(tattoo.getId(),
                tattoo.getPrice(), tattooistName, tattoo.getDescription(), isLiked, tattooistAddress, imageUrls);

        return getTattoosResDto;
    }

    private GetTattooResDto convertToGetTattooRes(Tattoo tattoo, String userId) {
        GetTattoosResDto getTattoosResDto = this.convertToGetTattoosRes(tattoo, userId);
        int likeCount = this.getLikeCount(getTattoosResDto.getId());

        GetTattooResDto getTattooResDto = new GetTattooResDto(
                getTattoosResDto.getId(), getTattoosResDto.getPrice(), getTattoosResDto.getTattooistName(), getTattoosResDto.getDescription(),
                getTattoosResDto.isLiked(), getTattoosResDto.getAddress(), getTattoosResDto.getImageUrls(), likeCount);

        return getTattooResDto;
    }

    private void saveImages(Long postId, List<String> imageUrls) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 타투 입니다.", ErrorInfo.TATTOO_NOT_FOUND_EXCEPTION));

        imageUrls.forEach(imageUrl -> {
            Image createdImage = new Image(imageUrl, post);
            this.imageRepository.save(createdImage);
        });
    }

    private List<String> uploadImages(List<MultipartFile> images) {
        List<String> imageUrls = new ArrayList<>();

        images.forEach(image -> {
            String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;
            File convertFile = new File(imageSavePath + "/tattoo/" + fileName);

            try {
                image.transferTo(convertFile);
                imageUrls.add(convertFile.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return imageUrls;
    }

    private List<String> getImageUrls(Long tattooId) {
        List<String> imageUrls = new ArrayList<>();
        List<Image> images = this.imageRepository.findByPostId(tattooId);

        images.forEach(image -> {
            imageUrls.add(image.getImageUrl());
        });

        return imageUrls;
    }

    private boolean isUserLikedTattoo(Long postId, String userId) {
        List<Likes> likes = this.postRepository.findById(postId).get().getLikes()
                .stream()
                .filter(like -> like.getUser().getId().equals(userId)).collect(Collectors.toList());

        return likes.size() > 0 ? true : false;
    }

    private int getLikeCount(Long tattooId) {
        List<Likes> likes = this.postRepository.findById(tattooId).get().getLikes();

        return likes.size();
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
