package com.spring.blackcat.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.InvalidTokenException;
import com.spring.blackcat.common.security.handler.CustomAccessDeniedHandler;
import com.spring.blackcat.common.security.jwt.JwtAuthenticationFilter;
import com.spring.blackcat.common.security.jwt.JwtAuthorizationFilter;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        JwtAuthorizationFilter jwtAuthorizationFilter =
                new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), userRepository);
        if (this.jwtAuthorizationFilter == null) {
            this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        }
        return jwtAuthorizationFilter;
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers("/api/v1/users/login");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        if (jwtAuthorizationFilter == null) {
            jwtAuthorizationFilter =
                    new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), userRepository);
        }

        return http.antMatcher("/**")
                .authorizeRequests()
                .mvcMatchers(OPTIONS, "/**").permitAll() // Preflight Request 허용해주기
                .antMatchers(GET, "/api/v1/categories").permitAll()
                .antMatchers(GET, "/api/v1/tattoos").permitAll()
                .antMatchers(GET, "/api/v1/tattoos/categories/**").permitAll()
                .antMatchers(GET, "/api/v1/tattoos/**").permitAll()
                .antMatchers(GET, "/api/v1/estimate/**").permitAll()
                .antMatchers(GET, "/api/v1/profile/**").permitAll()
                .antMatchers(POST, "/init").access("hasRole('ADMIN')")
                .antMatchers(POST, "/api/v1/magazines").access("hasRole('ADMIN')")
                .antMatchers(POST, "/api/v1/tattoos").access("hasRole('TATTOOIST') or hasRole('ADMIN')")
                .antMatchers("/api/v1/**").authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .addFilter(corsFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, JwtAuthorizationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {

                    InvalidTokenException ite = new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorInfo.INVALID_TOKEN_EXCEPTION);

                    Map<String, Object> errDetail = new HashMap<>();

                    errDetail.put("message", ite.getErrorInfo().getMessage());
                    errDetail.put("errorCode", ite.getErrorInfo().getErrorCode());
                    errDetail.put("statusCode", ite.getErrorInfo().getStatusCode());

                    response.setStatus((HttpStatus.UNAUTHORIZED.value()));
                    response.setContentType("application/json;charset=UTF-8");

                    objectMapper.writeValue(response.getWriter(), errDetail);
                }))
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .build();
    }
}
