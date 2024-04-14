package ru.vogu35.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.vogu35.backend.proxies.AuthService;
import org.springframework.http.*;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    private final AuthService authService;
    private static final String CLIENT_ID = "c7ca8491a7e3a60";
    private static final String CLIENT_SECRET = "ed482ae00f8b90129a2214898f1e4d33b93c17ac";
    private static final String AUTH_URL = "https://api.imgur.com/oauth2/authorize?client_id="+ CLIENT_ID + "&response_type=token";

    @Autowired
    public TestController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable String id) throws JsonProcessingException {
        authService.findByUserId(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/imgur/aapi")
    public ResponseEntity<?> imgurAccessToken() {
        String url = "https://api.imgur.com/oauth2/token";
        String clientId = "c7ca8491a7e3a60";
        String clientSecret = "ed482ae00f8b90129a2214898f1e4d33b93c17ac";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer");
        headers.set("Callback URL", "https://www.getpostman.com/oauth2/callback");
        headers.set("Access Token URL", "https://api.imgur.com/oauth2/token");
        headers.set("Client ID", clientId);
        headers.set("Client Secret", clientSecret);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

}
