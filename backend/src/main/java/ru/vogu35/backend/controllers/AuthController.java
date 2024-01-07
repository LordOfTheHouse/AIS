package ru.vogu35.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.vogu35.backend.entities.Teacher;
import ru.vogu35.backend.models.*;
import org.springframework.http.*;
import ru.vogu35.backend.proxies.KeycloakApiProxy;
import ru.vogu35.backend.services.auth.JwtService;
import ru.vogu35.backend.services.TeacherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final KeycloakApiProxy keycloakApiProxy;

    public AuthController(JwtService jwtService, KeycloakApiProxy keycloakApiProxy) {
        this.jwtService = jwtService;
        this.keycloakApiProxy = keycloakApiProxy;
    }

    /**
     * Авторизует пользователя
     *
     * @param loginRequest - данные для входа
     * @return токен пользователя в случе успеха
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody LoginRequest loginRequest) {
        ResponseEntity<String> userEntity = keycloakApiProxy.signIn(loginRequest);
        return new ResponseEntity<>(userEntity.getBody(), userEntity.getStatusCode());
    }

    /**
     * Регистрирует пользователя
     *
     * @param signupRequest - данные для регистрации
     * @return статус операции
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        if(keycloakApiProxy.signUp(signupRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Возврашает данные пользователя
     *
     * @return данные
     */
    @PreAuthorize("hasAnyRole('client_student', 'client_teacher')")
    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        UserResponse userResponse = new UserResponse(
                jwtService.getSubClaim(), jwtService.getPreferredUsernameClaim(),
                jwtService.getEmailClaim(), jwtService.getFirstNameClaim(),
                jwtService.getMiddleNameClaim(), jwtService.getLastNameClaim(),
                jwtService.getGroupIdClaim()
        );
        return ResponseEntity.ok(userResponse);
    }

}
