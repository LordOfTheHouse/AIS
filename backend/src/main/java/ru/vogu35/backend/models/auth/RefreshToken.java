package ru.vogu35.backend.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Токен для сброса
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RefreshToken {
    private String refresh_token;
}