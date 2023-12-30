package ru.vogu35.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Авторизует пользователя
     * @param loginRequest - данные для входа
     * @return токен пользователя в случе успеха
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody Object loginRequest) {
        return ResponseEntity.ok().build();
    }

    /**
     * Регистрирует пользователя
     * @param signupRequest - данные для регистрации
     * @return статус операции
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody String signupRequest) {
        return ResponseEntity.ok().build();
    }

    /**
     * Возврашает данные пользователя
     * @return данные
     */
    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        return ResponseEntity.ok().build();
    }


}
