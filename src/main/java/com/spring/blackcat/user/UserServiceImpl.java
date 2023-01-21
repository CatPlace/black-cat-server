package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.common.code.ImageType;
import com.spring.blackcat.common.code.ProviderType;
import com.spring.blackcat.common.code.Role;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.AddressNotFoundException;
import com.spring.blackcat.common.exception.custom.InvalidLoginInputException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.common.security.auth.OAuthService;
import com.spring.blackcat.common.security.jwt.JwtProvider;
import com.spring.blackcat.estimate.Estimate;
import com.spring.blackcat.estimate.EstimateRepository;
import com.spring.blackcat.image.ImageService;
import com.spring.blackcat.profile.Profile;
import com.spring.blackcat.profile.ProfileRepository;
import com.spring.blackcat.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OAuthService oAuthService;
    private final JwtProvider jwtProvider;

    private final ProfileRepository profileRepository;

    private final EstimateRepository estimateRepository;
    private final ImageService imageService;

    /**
     * 로그인
     */
    @Override
    public UserLoginResDto login(UserLoginReqDto userJoinReqDto) {
        String providerId = this.getProviderId(userJoinReqDto);
        User user = this.getUser(userJoinReqDto, providerId);
        String accessToken = this.jwtProvider.createAccessToken(user.getId());
        UserLoginResDto userLoginResDto = new UserLoginResDto(user.getId(), accessToken, user.getRole());

        return userLoginResDto;
    }

    @Override
    @Transactional
    public AdditionalInfoResDto addAdditionalInfo(AdditionalInfoReqDto additionalInfoReqDto, List<MultipartFile> images, Long userId) {
        System.out.println(additionalInfoReqDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));
        Address address = findUserAddress(additionalInfoReqDto.getAddressId());

        user.updateAdditionalInfo(additionalInfoReqDto.getName(), additionalInfoReqDto.getEmail(),
                additionalInfoReqDto.getPhoneNumber(), additionalInfoReqDto.getGender(), address);

        List<String> imageUrls = this.imageService.getImageUrls(ImageType.USER, user.getId());
        imageUrls = images == null ? imageUrls :
                imageUrls.isEmpty() ? this.imageService.saveImage(ImageType.USER, user.getId(), images) : updateImage(imageUrls.get(0), user, images);

        AdditionalInfoResDto additionalInfoResDto = new AdditionalInfoResDto(user.getName(),
                user.getEmail(), user.getPhoneNumber(), user.getGender(), address.getId(), imageUrls);

        return additionalInfoResDto;
    }

    private List<String> updateImage(String imageUrls, User user, List<MultipartFile> images) {
        String deletedImageUrl = this.imageService.deleteImage(imageUrls);

        return this.imageService.saveImage(ImageType.POST, user.getId(), images);
    }

    private Address findUserAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("존재하지 않는 주소입니다.", ErrorInfo.ADDRESS_NOT_FOUND_EXCEPTION));
    }

    @Override
    @Transactional
    public CreateTattooistResDto createTattooist(CreateTattooistReqDto createTattooistReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));

        Address address = addressRepository.findById(createTattooistReqDto.getAddressId()).orElseThrow();

        user.updateTattooistInfo(address, createTattooistReqDto.getOpenChatLink(), Role.TATTOOIST);

        CreateTattooistResDto createTattooistResDto = new CreateTattooistResDto(user.getAddress().getId(), user.getOpenChatLink());

        return createTattooistResDto;
    }

    @Override
    @Transactional
    public DeleteUserResDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));

        userRepository.delete(user);

        DeleteUserResDto deleteUserResDto = new DeleteUserResDto(user.getId());

        return deleteUserResDto;
    }

    @Override
    public ChangeRoleResDto changeRole(ChangeRoleReqDto changeRoleReqDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        String roleName = changeRoleReqDto.getRole();
        user.changeRole(Role.valueOf(roleName));

        return new ChangeRoleResDto(roleName);
    }

    @Override
    public ChangeNicknameResDto changeNickname(ChangeNicknameReqDto changeNicknameReqDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        String nickname = changeNicknameReqDto.getNickname();
        user.changeNickname(nickname);

        return new ChangeNicknameResDto(nickname);
    }

    @Override
    public ChangeAddressResDto changeAddress(ChangeAddressReqDto changeAddressReqDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Address address = addressRepository.findById(changeAddressReqDto.getAddressId()).orElseThrow();
        user.changeAddress(address);

        return new ChangeAddressResDto(address.getId());
    }

    private String getProviderId(UserLoginReqDto userJoinReqDto) {
        String providerId;

        if (userJoinReqDto.getProviderType().toString().equals(ProviderType.KAKAO.name())) {
            providerId = this.oAuthService.verifyKakao(userJoinReqDto.getProviderToken());
        } else if (userJoinReqDto.getProviderType().toString().equals(ProviderType.APPLE.name())) {
            providerId = this.oAuthService.verifyApple(userJoinReqDto.getProviderToken());
        } else {
            throw new InvalidLoginInputException("값이 유효하지 않은 로그인 요청입니다.", ErrorInfo.INVALID_LOGIN_INPUT_EXCEPTION);
        }

        return providerId;
    }

    private User getUser(UserLoginReqDto userJoinReqDto, String providerId) {

        return this.userRepository.findUserByProviderIdAndProviderType(providerId, userJoinReqDto.getProviderType())
                .orElseGet(() -> {
                    ProviderType providerType = userJoinReqDto.getProviderType();
                    String defaultNickname = providerType + "_" + UUID.randomUUID();
                    User createdUser = new User(providerId, providerType, defaultNickname, Role.BASIC, 1L, 1L);
                    this.userRepository.save(createdUser);
                    createProfile(createdUser);
                    createEstimate(createdUser);
                    return createdUser;
                });
    }

    private void createEstimate(User user) {
        Estimate estimate = new Estimate(user);

        this.estimateRepository.save(estimate);
    }

    private void createProfile(User user) {
        Profile profile = new Profile(user);

        this.profileRepository.save(profile);
    }
}
