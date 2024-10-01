package com.project.reelRadar.exception;

import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.project.reelRadar.exception.error.ErrorMessage.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException() {
        return ResponseEntity.status(NOT_FOUND)
                .body(USERNAME_NOT_FOUND);
    }

    @ExceptionHandler(ErrorWhileAuth.class)
    public ResponseEntity<String> handleErrorWhileAuth() {
        return ResponseEntity.status(BAD_REQUEST)
                .body(ERROR_WHILE_AUTH);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException() {
        return ResponseEntity.status(CONFLICT)
                .body(USER_ALREADY_EXISTS);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }
}