package com.spring.blackcat.common.security.config;

import com.spring.blackcat.common.security.config.jwt.JwtAuthenticationFilter;
import com.spring.blackcat.common.security.config.jwt.JwtAuthorizationFilter;
import com.spring.blackcat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
//    private final PrincipalOAuth2UserService principalOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository))
                .authorizeRequests()
                .antMatchers("/security/**")
                .access("hasRole('BASIC') or hasRole('TATTOOIST') or hasRole('ADMIN')")
                .anyRequest().permitAll();

        // TODO: OAuth 로그인
//        http.oauth2Login()
//                .userInfoEndpoint()
//                .userService(principalOAuth2UserService)
//                .and()
//                .permitAll()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();

        return http.build();
    }
}
