package com.spring.blackcat.profile;

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

        List<String> imageUrls = this.imageService.saveImage(profile, images);

        UpsertProfileResDto upsertProfileResDto = new UpsertProfileResDto(profile.getIntroduce(), imageUrls);

        return upsertProfileResDto;
    }
}
