package com.spring.blackcat.common.security.jwt;

import com.auth0.jwt.JWT;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.spring.blackcat.common.security.jwt.JwtProperties.EXPIRATION_TIME;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    private String createToken(final Long payload) {
        return JWT.create()
                .withSubject(payload.toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRATION_TIME)))
                .withClaim("id", payload)
                .sign(HMAC512(SECRET_KEY));
    }

    public String createAccessToken(final Long payload) {
        return createToken(payload);
    }

    public Long getAccessTokenPayload(String accessToken) {
        try {
            return JWT.require(HMAC512(SECRET_KEY))
                    .build()
                    .verify(accessToken)
                    .getClaim("id")
                    .asLong();

        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException | SignatureException e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorInfo.INVALID_TOKEN_EXCEPTION);
        }
    }

    public boolean validateToken(String accessToken) {
        try {
            return JWT.require(HMAC512(SECRET_KEY))
                    .build()
                    .verify(accessToken)
                    .getExpiresAt().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }
}
