package com.spring.blackcat.common.exception;

import com.spring.blackcat.common.exception.custom.CategoryNotFoundException;
import com.spring.blackcat.common.exception.custom.MagazineNotFoundException;
import com.spring.blackcat.common.exception.custom.TattooNotFoundException;
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

    @ExceptionHandler(value = {TattooNotFoundException.class})
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(TattooNotFoundException te) {
        ErrorResponse errorResponse = new ErrorResponse(te.getErrorInfo());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(te.getErrorInfo().getStatusCode()));
    }

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException ce) {
        ErrorResponse errorResponse = new ErrorResponse(ce.getErrorInfo());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ce.getErrorInfo().getStatusCode()));
    }
}
