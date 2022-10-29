package com.spring.blackcat.user;

import com.spring.blackcat.common.code.ProviderType;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.InvalidLoginInputException;
import com.spring.blackcat.common.security.auth.OAuthService;
import com.spring.blackcat.common.security.jwt.JwtProvider;
import com.spring.blackcat.user.dto.UserLoginReqDto;
import com.spring.blackcat.user.dto.UserLoginResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
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

    private String getProviderId(UserLoginReqDto userJoinReqDto) {
        String providerId;

        if (userJoinReqDto.getProviderType().equals(ProviderType.KAKAO.name())) {
            providerId = this.oAuthService.verifyKakao(userJoinReqDto.getProviderToken());
        } else if (userJoinReqDto.getProviderType().equals(ProviderType.APPLE.name())) {
            providerId = this.oAuthService.verifyApple(userJoinReqDto.getProviderToken());
        } else {
            throw new InvalidLoginInputException("값이 유효하지 않은 로그인 요청입니다.", ErrorInfo.INVALID_LOGIN_INPUT_EXCEPTION);
        }

        return providerId;
    }

    private User getUser(UserLoginReqDto userJoinReqDto, String providerId) {
        User user = this.userRepository.findUserByProviderIdAndProviderType(providerId, userJoinReqDto.getProviderType())
                .orElseGet(() -> {
                    User createdUser = new User(providerId, userJoinReqDto.getProviderType());

                    this.userRepository.save(createdUser);

                    return createdUser;
                });

        return user;
    }
}
