package ru.vogu35.backend.proxies;

import org.springframework.http.ResponseEntity;
import ru.vogu35.backend.models.auth.LoginRequest;
import ru.vogu35.backend.models.auth.RefreshToken;
import ru.vogu35.backend.models.auth.SignupRequest;
import ru.vogu35.backend.models.auth.UserResponse;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    Optional<UserResponse> findByUserId(String id);

    List<UserResponse> findUserByGroup(String groupName);

    ResponseEntity<String> signIn(LoginRequest loginRequest);

    boolean signUp(SignupRequest signupRequest);

    Optional<String> refreshTokenUser(RefreshToken refreshToken);

    Optional<UserResponse> getUserDetail();

}
