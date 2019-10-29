package com.onyourmind.OnYourMind.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), badRequest, ZonedDateTime.now());
        return new ResponseEntity<>(errorMessage, badRequest);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus notFoundStatus = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), notFoundStatus, ZonedDateTime.now());
        return new ResponseEntity<>(errorMessage, notFoundStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception e) {
        HttpStatus internalServerErrorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage errorMessage = new ErrorMessage("Internal server error.", internalServerErrorCode, ZonedDateTime.now());
        return new ResponseEntity<>(errorMessage, internalServerErrorCode);
    }

}
