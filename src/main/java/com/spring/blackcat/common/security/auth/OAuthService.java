package com.spring.blackcat.common.security.auth;

import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuthService {
    private final RestTemplateService restTemplateService;

    private static final String KAKAO_OAUTH_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer ";

    private static final String APPLE_ID_KEY = "sub";

    public String verifyKakao(String accessToken) {
        try {
            Map<String, List<String>> headers = new HashMap<>();
            headers.put(HEADER, List.of(TOKEN_TYPE + accessToken));
            MultiValueMap<String, String> header = CollectionUtils.toMultiValueMap(headers);
            ResponseEntity<KakaoResponse> response = restTemplateService.get(
                    KAKAO_OAUTH_URL,
                    new HttpHeaders(header),
                    KakaoResponse.class
            );

            return response.getBody().getId();
        } catch (Exception e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorInfo.INVALID_TOKEN_EXCEPTION);
        }
    }

    public String verifyApple(String idToken) {
        try {
            JsonParser parser = JsonParserFactory.getJsonParser();
            String payload = new String(Base64Utils.decodeFromString(idToken.split("\\.")[1]));

            return (String) parser.parseMap(payload).get(APPLE_ID_KEY);
        } catch (Exception e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorInfo.INVALID_TOKEN_EXCEPTION);
        }

    }
}
