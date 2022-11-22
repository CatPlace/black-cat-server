package com.spring.blackcat.common.security.jwt;

public interface JwtProperties {
    String HEADER_STRING = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    Long EXPIRATION_TIME = 60000L * 60L * 24L;
}
