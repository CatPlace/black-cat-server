package com.spring.blackcat.common.exception;

import com.spring.blackcat.common.exception.custom.MagazineNotFoundException;
import com.spring.blackcat.common.exception.custom.PostNotFoundException;
import com.spring.blackcat.common.exception.custom.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {MagazineNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleMagazineNotFoundException(MagazineNotFoundException me) {
        ErrorResponse errorResponse = new ErrorResponse(me.getErrorInfo());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(me.getErrorInfo().getStatusCode()));
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ue) {
        ErrorResponse errorResponse = new ErrorResponse(ue.getErrorInfo());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ue.getErrorInfo().getStatusCode()));
    }

    @ExceptionHandler(value = {PostNotFoundException.class})
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException pe) {
        ErrorResponse errorResponse = new ErrorResponse(pe.getErrorInfo());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(pe.getErrorInfo().getStatusCode()));
    }
}
