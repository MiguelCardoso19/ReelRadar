package com.project.reelRadar.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleUsernameNotFoundException() {
        ResponseEntity<String> response = globalExceptionHandler.handleUsernameNotFoundException();

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Username not found", response.getBody());
    }

    @Test
    public void testHandleErrorWhileAuth() {
        ResponseEntity<String> response = globalExceptionHandler.handleErrorWhileAuth();

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Error while authenticating", response.getBody());
    }

    @Test
    public void testHandleUserAlreadyExistsException() {
        ResponseEntity<String> response = globalExceptionHandler.handleUserAlreadyExistsException();

        assertEquals(CONFLICT, response.getStatusCode());
        assertEquals("This user already exists", response.getBody());
    }

    @Test
    public void testHandleUsernameAlreadyExistsException() {
        ResponseEntity<String> response = globalExceptionHandler.handleUsernameAlreadyExistsException();

        assertEquals(CONFLICT, response.getStatusCode());
        assertEquals("This username already exists", response.getBody());
    }

    @Test
    public void testHandleEmailAlreadyExistsException() {
        ResponseEntity<String> response = globalExceptionHandler.handleEmailAlreadyExistsException();

        assertEquals(CONFLICT, response.getStatusCode());
        assertEquals("This email already exists", response.getBody());
    }
}