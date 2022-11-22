package com.spring.blackcat.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blackcat.common.exception.custom.UnauthorizedUserException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.spring.blackcat.common.exception.ErrorInfo.UNAUTHORIZED_USER_EXCEPTION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException {

        UnauthorizedUserException exception =
                new UnauthorizedUserException("인가되지 않은 사용자입니다.", UNAUTHORIZED_USER_EXCEPTION);

        Map<String, Object> errDetail = new HashMap<>();

        errDetail.put("message", exception.getErrorInfo().getMessage());
        errDetail.put("errorCode", exception.getErrorInfo().getErrorCode());
        errDetail.put("statusCode", exception.getErrorInfo().getStatusCode());

        response.setStatus((FORBIDDEN.value()));
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errDetail);
    }
}
