package ru.vogu35.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vogu35.backend.exseptions.LoginUserException;

/**
 * Контроллер для перехвата исключений
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> exceptionRuntimeHandler(RuntimeException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginUserException.class)
    public ResponseEntity<String> exceptionLoginHandler(LoginUserException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
}
