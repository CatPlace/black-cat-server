package com.spring.blackcat.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blackcat.common.exception.ErrorInfo;
import com.spring.blackcat.common.exception.custom.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIX_BEARER = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var accessToken = resolveToken(request);

        if (accessToken != null && jwtProvider.validateToken(accessToken)) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(jwtProvider.getAccessTokenPayload(accessToken)));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities()));
            } catch (Exception e) {
                InvalidTokenException ite = new InvalidTokenException("유효하지 않은 토큰입니다.", ErrorInfo.INVALID_TOKEN_EXCEPTION);
                Map<String, Object> errDetail = new HashMap<>();
                errDetail.put("message", ite.getErrorInfo().getMessage());
                errDetail.put("errorCode", ite.getErrorInfo().getErrorCode());
                errDetail.put("statusCode", ite.getErrorInfo().getStatusCode());

                response.setStatus((HttpStatus.UNAUTHORIZED.value()));
                response.setContentType("application/json;charset=UTF-8");
                objectMapper.writeValue(response.getWriter(), errDetail);
                System.out.println(errDetail);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}