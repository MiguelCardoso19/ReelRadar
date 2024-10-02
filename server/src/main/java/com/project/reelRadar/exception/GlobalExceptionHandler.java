package com.project.reelRadar.exception;

import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.exception.customException.UsernameAlreadyExistsException;
import com.project.reelRadar.exception.customException.EmailAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExistsException() {
        return ResponseEntity.status(CONFLICT)
                .body(USERNAME_ALREADY_EXISTS);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException() {
        return ResponseEntity.status(CONFLICT)
                .body(EMAIL_ALREADY_EXISTS);
    }
}