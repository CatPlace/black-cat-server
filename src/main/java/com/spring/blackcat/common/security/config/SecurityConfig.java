package com.spring.blackcat.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.InvalidTokenException;
import com.spring.blackcat.common.security.jwt.JwtAuthenticationFilter;
import com.spring.blackcat.common.security.jwt.JwtAuthorizationFilter;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final UserRepository userRepository;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/api/v1/users/login"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        return http.antMatcher("/**")
                .authorizeRequests()
                .mvcMatchers(OPTIONS, "/**").permitAll() // Preflight Request 허용해주기
                .antMatchers(POST, "/init").access("hasRole('ADMIN')")
                .antMatchers("/api/v1/**").authenticated()
                .antMatchers(POST, "/api/v1/magazines").access("hasRole('ADMIN')")
                .antMatchers(POST, "/api/v1/tattoos").access("hasRole('TATTOOIST') or hasRole('ADMIN')")
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager, userRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, AuthorizationFilter.class)
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
//                .accessDeniedHandler(((request, response, accessDeniedException) -> {
//                }))
                .and().build();
    }
}
