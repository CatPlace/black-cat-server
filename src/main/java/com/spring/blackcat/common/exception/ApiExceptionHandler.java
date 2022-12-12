package com.spring.blackcat.common.exception;

import com.spring.blackcat.common.exception.custom.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import static com.spring.blackcat.common.exception.ErrorInfo.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(UNKNOWN_EXCEPTION);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestPartException.class})
    public ResponseEntity<ErrorResponse> methodValidException() {
        ErrorResponse errorResponse = new ErrorResponse(NULL_INPUT_EXCEPTION);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> notReadableException() {
        ErrorResponse errorResponse = new ErrorResponse(INVALID_INPUT_EXCEPTION);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorInfo());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorInfo().getStatusCode()));
    }
}
