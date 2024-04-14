package ru.vogu35.backend.proxies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.models.auth.LoginRequest;
import ru.vogu35.backend.models.auth.RefreshToken;
import ru.vogu35.backend.models.auth.SignupRequest;
import ru.vogu35.backend.models.auth.UserResponse;

import java.util.List;
import java.util.Optional;

@Slf4j
//@Primary
@Service
public class LocalAuth implements AuthService {
    private final UserResponse testUser = new UserResponse.UserResponseBuilder("1")
                                                .username("test")
                                                .email("ke.s2013@123.ru")
                                                .build();
    @Override
    public Optional<UserResponse> findByUserId(String id) {
        return Optional.of(testUser);
    }

    @Override
    public List<UserResponse> findUserByGroup(String groupName) {
        return List.of(testUser);
    }

    @Override
    public ResponseEntity<String> signIn(LoginRequest loginRequest) {
        return ResponseEntity.ok("1");
    }

    @Override
    public boolean signUp(SignupRequest signupRequest) {
        return true;
    }

    @Override
    public Optional<String> refreshTokenUser(RefreshToken refreshToken) {
        return Optional.of("1");
    }

    @Override
    public Optional<UserResponse> getUserDetail() {
        log.info("{}", testUser);
        return Optional.of(testUser);
    }
}
