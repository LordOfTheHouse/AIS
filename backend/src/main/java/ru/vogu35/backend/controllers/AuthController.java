package ru.vogu35.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.vogu35.backend.models.LoginRequest;
import org.springframework.http.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final String clientId = "login-app";
    private final String grantType = "password";
    private final String keycloakTokenUrl = "http://localhost:8080/realms/customer-realm/protocol/openid-connect/token";

    /**
     * Авторизует пользователя
     * @param loginRequest - данные для входа
     * @return токен пользователя в случе успеха
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody LoginRequest loginRequest) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", grantType);
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", loginRequest.getUsername());
        tokenBody.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            return new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
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
