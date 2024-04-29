package ru.vogu35.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.models.auth.LoginRequest;
import ru.vogu35.backend.models.auth.RefreshToken;
import ru.vogu35.backend.models.auth.SignupRequest;
import ru.vogu35.backend.proxies.AuthService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Авторизует пользователя
     *
     * @param loginRequest - данные для входа
     * @return токен пользователя в случе успеха
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody LoginRequest loginRequest) {
        ResponseEntity<String> userEntity = authService.signIn(loginRequest);
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
        if(authService.signUp(signupRequest)) {
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
        Optional<String> newToken = authService.refreshTokenUser(refreshToken);
        return newToken
                .map(token -> ResponseEntity.accepted().body(token))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasAnyRole('client_student', 'client_teacher')")
    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        if(authService.getUserDetail().isPresent()){
            log.info("user: {}", authService.getUserDetail().get());
            return ResponseEntity.ok(authService.getUserDetail().get());
        }
        return ResponseEntity.badRequest().build();
    }

}
