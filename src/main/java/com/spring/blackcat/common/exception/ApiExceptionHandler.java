package com.spring.blackcat.common.exception;

import com.spring.blackcat.common.exception.custom.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.spring.blackcat.common.exception.ErrorInfo.UNKNOWN_EXCEPTION;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(UNKNOWN_EXCEPTION);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorInfo());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorInfo().getStatusCode()));
    }

//    @ExceptionHandler(value = {MagazineNotFoundException.class})
//    public ResponseEntity<ErrorResponse> handleMagazineNotFoundException(MagazineNotFoundException me) {
//        ErrorResponse errorResponse = new ErrorResponse(me.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(me.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {UserNotFoundException.class})
//    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ue) {
//        ErrorResponse errorResponse = new ErrorResponse(ue.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ue.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {TattooNotFoundException.class})
//    public ResponseEntity<ErrorResponse> handlePostNotFoundException(TattooNotFoundException te) {
//        ErrorResponse errorResponse = new ErrorResponse(te.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(te.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {CategoryNotFoundException.class})
//    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException ce) {
//        ErrorResponse errorResponse = new ErrorResponse(ce.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ce.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {ImageUploadFailedException.class})
//    public ResponseEntity<ErrorResponse> handleImageUploadFailedException(ImageUploadFailedException ie) {
//        ErrorResponse errorResponse = new ErrorResponse(ie.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ie.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {InvalidLoginInputException.class})
//    public ResponseEntity<ErrorResponse> handleInvalidLoginInputException(InvalidLoginInputException iie) {
//        ErrorResponse errorResponse = new ErrorResponse(iie.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(iie.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {InvalidTokenException.class})
//    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ite) {
//        ErrorResponse errorResponse = new ErrorResponse(ite.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ite.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {IllegalTokenException.class})
//    public ResponseEntity<ErrorResponse> handleIllegalTokenException(IllegalTokenException ilte) {
//        ErrorResponse errorResponse = new ErrorResponse(ilte.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ilte.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {PostTypeNotFoundException.class})
//    public ResponseEntity<ErrorResponse> handlePostTypeNotFoundException(PostTypeNotFoundException e) {
//        ErrorResponse errorResponse = new ErrorResponse(e.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorInfo().getStatusCode()));
//    }
//
//    @ExceptionHandler(value = {UnauthorizedUserException.class})
//    public ResponseEntity<ErrorResponse> handleUnauthorizedUserException(UnauthorizedUserException e) {
//        ErrorResponse errorResponse = new ErrorResponse(e.getErrorInfo());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorInfo().getStatusCode()));
//    }
}
