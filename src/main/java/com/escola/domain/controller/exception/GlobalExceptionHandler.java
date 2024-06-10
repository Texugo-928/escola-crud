package com.escola.domain.controller.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFoundException(NoSuchElementException notFoundException) {
        String message = "Resource Not Found";
        logger.error(message, notFoundException);

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    } 
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleTreatedException(CustomException treatedException) {
        String message = treatedException.getMessage();
        logger.error(message, treatedException);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } 

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleUnexpectedException(Throwable unexpectedException) {
        String message = "Unexpected Server Error, see the logs.";
        logger.error(message, unexpectedException);
        
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
