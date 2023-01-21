package com.spring.blackcat.profile;

import com.spring.blackcat.common.code.ImageType;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.image.ImageService;
import com.spring.blackcat.profile.dto.UpsertProfileReqDto;
import com.spring.blackcat.profile.dto.UpsertProfileResDto;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ProfileRepository profileRepository;

    @Override
    public UpsertProfileResDto upsertTattooistProfile(Long userId, UpsertProfileReqDto upsertProfileReqDto, List<MultipartFile> images) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));

        Profile profile = this.profileRepository.findByUserId(userId).get();
        profile.updateProfile(upsertProfileReqDto.getIntroduce());

        List<String> imageUrls = this.imageService.getImageUrls(ImageType.POST, profile.getId());
        imageUrls = images == null ? imageUrls :
                imageUrls.isEmpty() ? this.imageService.saveImage(ImageType.POST, profile.getId(), images) : updateImage(imageUrls.get(0), profile, images);

        UpsertProfileResDto upsertProfileResDto = new UpsertProfileResDto(profile.getIntroduce(), imageUrls);

        return upsertProfileResDto;
    }

    private List<String> updateImage(String imageUrls, Profile profile, List<MultipartFile> images) {
        String deletedImageUrl = this.imageService.deleteImage(imageUrls);

        return this.imageService.saveImage(ImageType.POST, profile.getId(), images);
    }

    @Override
    public UpsertProfileResDto getTattooistProfile(Long userId) {
        Profile profile = this.profileRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));

        List<String> imageUrls = this.imageService.getImageUrls(ImageType.POST, profile.getId());

        UpsertProfileResDto upsertProfileResDto = new UpsertProfileResDto(profile.getIntroduce(), imageUrls);

        return upsertProfileResDto;
    }
}
