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
import ru.vogu35.backend.models.*;
import org.springframework.http.*;
import ru.vogu35.backend.services.JwtService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final String clientId = "login-app";
    private final String grantType = "password";
    private final String keycloakTokenUrl = "http://localhost:8080/realms/ais-realm/protocol/openid-connect/token";
    private final String keycloakCreateUserUrl = "http://localhost:8080/admin/realms/ais-realm/users";
    private final JwtService jwtService;

    @Autowired
    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Авторизует пользователя
     *
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
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);

            return ResponseEntity.ok(tokenResponseEntity.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Регистрирует пользователя
     *
     * @param signupRequest - данные для регистрации
     * @return статус операции
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        log.info("Выводим данные о клиенте {}", signupRequest);
        HttpEntity<UserRequest> userEntity = getUserRequestHttpEntity(signupRequest);

        log.info("Http entity: {}", userEntity);
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl, HttpMethod.POST, userEntity, String.class);
            log.info("Результат отправки на keycloak: {}", userResponseEntity.getStatusCode());

            return new ResponseEntity<>(userResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private HttpEntity<UserRequest> getUserRequestHttpEntity(SignupRequest signupRequest) throws JsonProcessingException {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(signupRequest.getUsername());
        userRequest.setFirstName(signupRequest.getFirstName());
        userRequest.setLastName(signupRequest.getLastName());
        userRequest.setEmail(signupRequest.getEmail());
        userRequest.setEnabled(true);

        Attributes attributes = new Attributes();
        attributes.setGroupName(signupRequest.getGroupName());
        attributes.setMiddleName(signupRequest.getMiddleName());
        userRequest.setAttributes(attributes);

        Credential credential = new Credential();
        credential.setType(grantType);
        credential.setValue(signupRequest.getPassword());

        List<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        userRequest.setCredentials(credentials);

        HttpHeaders userHeaders = getHttpHeadersAdmin();
        return new HttpEntity<>(userRequest, userHeaders);
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

    private HttpHeaders getHttpHeadersAdmin() throws JsonProcessingException {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(signInUser(new LoginRequest("admin", "11111")).getBody());
        String accessToken = rootNode.path("access_token").asText();
        userHeaders.setBearerAuth(accessToken);
        return userHeaders;
    }
}
