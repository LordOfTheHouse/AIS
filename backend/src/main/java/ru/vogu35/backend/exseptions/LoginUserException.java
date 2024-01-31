package ru.vogu35.backend.exseptions;

public class LoginUserException extends RuntimeException {
    public LoginUserException(String message) {
        super(message);
    }
}
