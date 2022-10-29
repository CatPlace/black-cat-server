package com.spring.blackcat.common.security.config.oauth;

import com.spring.blackcat.common.code.Role;
import com.spring.blackcat.common.security.config.auth.PrincipalDetails;
import com.spring.blackcat.common.security.config.oauth.provider.FacebookUserInfo;
import com.spring.blackcat.common.security.config.oauth.provider.GoogleUserInfo;
import com.spring.blackcat.common.security.config.oauth.provider.NaverUserInfo;
import com.spring.blackcat.common.security.config.oauth.provider.OAuth2UserInfo;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo;

        if ("google".equals(userRequest.getClientRegistration().getRegistrationId())) {
            log.info("Google Login");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if ("facebook".equals(userRequest.getClientRegistration().getRegistrationId())) {
            log.info("Facebook Login");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if ("naver".equals(userRequest.getClientRegistration().getRegistrationId())) {
            log.info("Naver Login");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        } else {
            throw new IllegalArgumentException("지원되지 않는 OAuth 로그인 방식입니다.");
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String id = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());
        String name = oAuth2UserInfo.getName();
        Role role = Role.BASIC;

        User user = userRepository.findById(id).orElse(null);

        // 기본 정보를 이용한 자동 회원가입
        if (user == null) {
            System.out.println("최초 OAuth 로그인입니다. 자동 회원가입을 진행합니다.");
            user = new User(id, password, name, role, null, "SYSTEM", "SYSTEM");
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
