package ru.vogu35.backend.proxies;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import ru.vogu35.backend.models.LoginRequest;
import ru.vogu35.backend.models.SignupRequest;
import ru.vogu35.backend.models.UserResponse;

import java.util.Optional;

public interface KeycloakApiProxy {
    Optional<UserResponse> findByUserId(String id);
    ResponseEntity<String> signIn(LoginRequest loginRequest);
    boolean signUp(SignupRequest signupRequest) throws JsonProcessingException;

}