package com.spring.blackcat.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // TODO: 정책 협의 및 변경 필요
        config.addAllowedOrigin("*"); // IP
        config.addAllowedHeader("*"); // Header
        config.addAllowedMethod("*"); // HTTP Method
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
