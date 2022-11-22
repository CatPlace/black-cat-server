package com.spring.blackcat.common.security.jwt;

import com.auth0.jwt.JWT;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.spring.blackcat.common.security.jwt.JwtProperties.HEADER_STRING;
import static com.spring.blackcat.common.security.jwt.JwtProperties.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwtHeader = request.getHeader(HEADER_STRING);

        if (jwtHeader == null || !jwtHeader.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");

        Long userId = JWT.require(HMAC512(SECRET_KEY))
                .build()
                .verify(jwtToken)
                .getClaim("id")
                .asLong();

        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow();
            UserPrincipal userPrincipal = new UserPrincipal(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userPrincipal, null, userPrincipal.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
