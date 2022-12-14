package com.spring.blackcat.common.security.interceptor;

import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.IllegalTokenException;
import com.spring.blackcat.common.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String SPACE = " ";
    private static final String BEARER = "Bearer";
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            String[] authorizations = Objects.requireNonNull(webRequest.getHeader(AUTHORIZATION)).split(SPACE);
            String type = authorizations[HEADER_KEY_INDEX];
            String accessToken = authorizations[HEADER_VALUE_INDEX];

            if (!type.equalsIgnoreCase(BEARER)) {
                throw new IllegalArgumentException();
            }

            return jwtProvider.getAccessTokenPayload(accessToken);

        } catch (Exception e) {
            throw new IllegalTokenException("토큰이 없거나 타입이 잘못되었습니다.", ErrorInfo.ILLEGAL_TOKEN_EXCEPTION);
        }
    }
}
