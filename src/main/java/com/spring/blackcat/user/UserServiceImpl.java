package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.address.AddressRepository;
import com.spring.blackcat.common.code.ProviderType;
import com.spring.blackcat.common.code.Role;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.InvalidLoginInputException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import com.spring.blackcat.common.security.auth.OAuthService;
import com.spring.blackcat.common.security.jwt.JwtProvider;
import com.spring.blackcat.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OAuthService oAuthService;
    private final JwtProvider jwtProvider;

    /**
     * 로그인
     */
    @Override
    public UserLoginResDto login(UserLoginReqDto userJoinReqDto) {
        String providerId = this.getProviderId(userJoinReqDto);
        User user = this.getUser(userJoinReqDto, providerId);
        String accessToken = this.jwtProvider.createAccessToken(user.getId());
        UserLoginResDto userLoginResDto = new UserLoginResDto(user.getId(), accessToken);

        return userLoginResDto;
    }

    @Override
    @Transactional
    public AdditionalInfoResDto addAdditionalInfo(AdditionalInfoReqDto additionalInfoReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));

        checkUserInfo(user.getDateOfBirth());

        user.updateAdditionalInfo(additionalInfoReqDto.getNickname(), additionalInfoReqDto.getDateOfBirth(), additionalInfoReqDto.getGender());

        AdditionalInfoResDto additionalInfoResDto = new AdditionalInfoResDto(user.getNickname(), user.getDateOfBirth(), user.getGender());

        return additionalInfoResDto;
    }

    @Override
    @Transactional
    public CreateTattooistResDto createTattooist(CreateTattooistReqDto createTattooistReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자 입니다.", ErrorInfo.USER_NOT_FOUND_EXCEPTION));

        checkUserInfo(user.getAddress());

        Address address = addressRepository.findById(createTattooistReqDto.getAddressId()).orElseThrow();

        user.updateTattooistInfo(address, createTattooistReqDto.getOpenChatLink(), Role.TATTOOIST);

        CreateTattooistResDto createTattooistResDto = new CreateTattooistResDto(user.getAddress().getId(), user.getOpenChatLink());

        return createTattooistResDto;
    }

    private static void checkUserInfo(Object userCheck) {
        if (userCheck != null) {
            throw new InvalidLoginInputException("이미 추가 정보를 입력한 사용자입니다.", ErrorInfo.ALREADY_EXIST_ADDITIONAL_INFO_EXCEPTION);
        }
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
                    return createdUser;
                });
    }
}
