package com.spring.blackcat.common.security.jwt;

import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    //@TODO: Property 변경
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    private final Long jwtValidTime = 6000000 * 30L;

    private String createToken(final Long payload, final String secretKey, final Long validTime) {
        return Jwts.builder()
                .setSubject(String.valueOf(payload))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .compact();
    }

    public String createAccessToken(final long payload) {
        return createToken(payload, jwtSecretKey, jwtValidTime);
    }

    public Long getAccessTokenPayload(String accessToken) {
        try {
            var claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(accessToken)
                    .getBody();

            return Long.parseLong(claims.getSubject());
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException |
                 SignatureException e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorInfo.INVALID_TOKEN_EXCEPTION);
        }
    }

    public boolean validateToken(String accessToken) {
        try {
            var claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(accessToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
