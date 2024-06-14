package com.escola.domain.controller.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new GlobalExceptionHandler();
    }

    // TODO: Testar algo relacionado ao Logger

    @Test
    public void handleNotFoundExceptionTest() {
        // Arrange
        NoSuchElementException exception = new NoSuchElementException("Resource Not Found");
    
        // Act
        ResponseEntity<String> response = handler.handleNotFoundException(exception);
    
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
        assertEquals("Resource Not Found", response.getBody());

    }

    @Test
    public void handleTreatedExceptionTest() {
        // Arrange
        CustomException exception = new CustomException("O atributo segmento não pode ser nulo ou vazio");
    
        // Act
        ResponseEntity<String> response = handler.handleTreatedException(exception);
    
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
        assertEquals(exception.getMessage(), response.getBody());

    }

    @Test
    public void handleUnexpectedExceptionTest() {
        // Arrange
        Exception exception = new Exception();
    
        // Act
        ResponseEntity<String> response = handler.handleUnexpectedException(exception);
    
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
        assertEquals("Unexpected Server Error, see the logs.", response.getBody());

    }

}
