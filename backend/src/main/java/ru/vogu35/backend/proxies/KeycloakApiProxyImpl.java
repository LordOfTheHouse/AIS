package ru.vogu35.backend.proxies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.vogu35.backend.entities.AdminToken;
import ru.vogu35.backend.exseptions.LoginUserException;
import ru.vogu35.backend.exseptions.UserNotFoundException;
import ru.vogu35.backend.models.*;
import ru.vogu35.backend.models.auth.*;
import ru.vogu35.backend.services.auth.AdminTokenService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class KeycloakApiProxyImpl implements KeycloakApiProxy {
    private final String clientId = "login-app";
    private final String grantType = "password";
    private final String keycloakGetUserUrl = "http://localhost:8080/admin/realms/ais-realm/users/";
    private final String keycloakCreateUserUrl = "http://localhost:8080/admin/realms/ais-realm/users";
    private final String keycloakTokenUrl = "http://localhost:8080/realms/ais-realm/protocol/openid-connect/token";
    private final AdminTokenService adminTokenService;

    @Autowired
    public KeycloakApiProxyImpl(AdminTokenService adminTokenService) {
        this.adminTokenService = adminTokenService;
    }

    @Override
    public Optional<UserResponse> findByUserId(String id) {
        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<Void> userEntity = new HttpEntity<>(null, userHeaders);
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakGetUserUrl + id,
                    HttpMethod.GET, userEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            UserInfo userInfo = objectMapper.readValue(userResponseEntity.getBody(), UserInfo.class);
            log.info("user: {}", userInfo);
            return Optional.of(new UserResponse(userInfo));
        } catch (Exception ex) {
            return Optional.empty();
        }


    }

    @Override
    public List<UserResponse> findUserByGroup(String groupName) {
        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<?> resetEntity = new HttpEntity<>(null, userHeaders);
        log.info("Http entity: {}", resetEntity);
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl + "?q=groupName:" + groupName,
                    HttpMethod.GET, resetEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode usersNode = objectMapper.readTree(userResponseEntity.getBody());
            if (!usersNode.isArray() || usersNode.size() <= 0) {
                throw new UserNotFoundException("В группе нет ни одного студента");
            }

            List<UserResponse> usersInfo = StreamSupport.stream(usersNode.spliterator(), false)
                    .map(node -> {
                        try {
                            return new UserResponse(objectMapper.readValue(node.toString(), UserInfo.class));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                    })
                    .collect(Collectors.toList());
            log.info("users: {}", usersInfo);
            return usersInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return List.of();
    }

    @Override
    public ResponseEntity<String> signIn(LoginRequest loginRequest) {
        log.info("Вход клиента с логином: {}", loginRequest.getUsername());
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", grantType);
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", loginRequest.getUsername());
        tokenBody.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);
        } catch (Exception ex) {
            throw new LoginUserException("Ошибка логина или пароля");
        }
        return responseEntity;
    }

    public Optional<String> refreshTokenUser(RefreshToken refreshToken) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "refresh_token");
        tokenBody.add("client_id", clientId);
        tokenBody.add("refresh_token", refreshToken.getRefresh_token());

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);
            log.info("result refresh: {}", tokenResponseEntity.getStatusCode());
            return Optional.ofNullable(tokenResponseEntity.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean signUp(SignupRequest signupRequest) throws JsonProcessingException {
        log.info("Выводим данные о клиенте {}", signupRequest);
        HttpEntity<UserRequest> userEntity = getUserRequestHttpEntity(signupRequest);
        try {
            new RestTemplate().exchange(keycloakCreateUserUrl, HttpMethod.POST, userEntity, String.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String checkAndUpdateOrderTokens() {
        Optional<AdminToken> orderToken = adminTokenService.findById(1);

        if (orderToken.isEmpty()) {
            log.info("Токен заказа отсутствует, отправка запроса на получение токена");
            return updateOrderTokens();
        } else {
            LocalDateTime currentDateTime = LocalDateTime.now();
            if (orderToken.stream().allMatch(token -> currentDateTime.isBefore(token.getTokenExpiration()))) {
                log.info("Список токенов не пуст и токен действителен. Нет необходимости обновлять токен заказа");
                return orderToken.get().getAccessToken();
            } else {
                log.info("Список токенов содержит токен с истекшим сроком действия. Отправка запроса на получение токена");
                return updateOrderTokens();
            }
        }

    }

    public String updateOrderTokens() {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", grantType);
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", "admin");
        tokenBody.add("password", "11111");

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    "http://localhost:8080/realms/ais-realm/protocol/openid-connect/token",
                    HttpMethod.POST, tokenEntity, String.class);

            String jsonResponse = tokenResponseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            String accessToken = jsonNode.get("access_token").asText();

            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(14);

            AdminToken newToken = new AdminToken(1, accessToken, expirationTime);

            adminTokenService.save(newToken);
            log.info("Токен заказа был обновлён успешно");
            return newToken.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
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

    private HttpHeaders getHttpHeadersAdmin() {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        checkAndUpdateOrderTokens();
        String accessToken = checkAndUpdateOrderTokens();
        userHeaders.setBearerAuth(accessToken);
        return userHeaders;
    }
}