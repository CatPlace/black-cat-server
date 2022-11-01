package com.spring.blackcat.common.security.jwt;

import com.auth0.jwt.JWT;
import com.spring.blackcat.user.User;
import com.spring.blackcat.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    private final String HEADER_STRING = "Authorization";

    private final String PREFIX_BEARER = "Bearer ";

    private String jwtSecretKey = "blackCatTattooApplicationJwtSecretKeySpringServer";

    private final Long jwtValidTime = 6000000 * 30L;

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwtHeader = request.getHeader(HEADER_STRING);

        if (jwtHeader == null || !jwtHeader.startsWith(PREFIX_BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(HEADER_STRING).replace(PREFIX_BEARER, "");

        // TODO: JWT 토큰 변경
        Long userId = JWT.require(HMAC256(jwtSecretKey))
                .build()
                .verify(jwtToken)
                .getClaim("id")
                .asLong();

        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow();
            UserPrincipal userPrincipal = new UserPrincipal(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
