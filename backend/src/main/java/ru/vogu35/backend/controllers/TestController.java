package ru.vogu35.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.proxies.KeycloakApiProxy;
import org.springframework.http.*;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    private final KeycloakApiProxy keycloakApiProxy;

    @Autowired
    public TestController(KeycloakApiProxy keycloakApiProxy) {
        this.keycloakApiProxy = keycloakApiProxy;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable String id) throws JsonProcessingException {
        keycloakApiProxy.findByUserId(id);
        return ResponseEntity.ok().build();
    }

}
