package com.spring.blackcat.common.security.config.jwt;

public interface JwtProperties {
    String SECRET = "blackCat";
    int EXPIRATION_TIME = 60000 * 30; // 30분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
