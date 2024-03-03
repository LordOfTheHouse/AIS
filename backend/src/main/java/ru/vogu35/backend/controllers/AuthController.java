package ru.vogu35.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.models.auth.LoginRequest;
import ru.vogu35.backend.models.auth.RefreshToken;
import ru.vogu35.backend.models.auth.SignupRequest;
import ru.vogu35.backend.models.auth.UserResponse;
import ru.vogu35.backend.proxies.KeycloakApiProxy;
import ru.vogu35.backend.services.auth.JwtService;

import java.util.Optional;

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
    public ResponseEntity<String> signUpUser(@RequestBody SignupRequest signupRequest) {
        if(keycloakApiProxy.signUp(signupRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUserInfo(@PathVariable(name = "id") String idUser) {

        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshUser(@RequestBody RefreshToken refreshToken) {
        log.info("refresh token");
        Optional<String> newToken = keycloakApiProxy.refreshTokenUser(refreshToken);
        return newToken
                .map(token -> ResponseEntity.accepted().body(token))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Возврашает данные пользователя
     *
     * @return данные
     */
    @PreAuthorize("hasAnyRole('client_student', 'client_teacher')")
    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        log.info("user: {}", jwtService.getGroupIdClaim());
        UserResponse userResponse = new UserResponse(
                jwtService.getSubClaim(), jwtService.getPreferredUsernameClaim(),
                jwtService.getEmailClaim(), jwtService.getFirstNameClaim(),
                jwtService.getMiddleNameClaim(), jwtService.getLastNameClaim(),
                jwtService.getGroupIdClaim(), jwtService.getPictureClaim()
        );

        return ResponseEntity.ok(userResponse);
    }

}
