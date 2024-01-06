package ru.vogu35.backend.services;

import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Возвращает данные пользователя из токена
 */
public interface  JwtService {
    /**
     * Возврашает id пользователя по токену
     * @return id пользователя
     */
    String getSubClaim();

    /**
     * Возврашает email пользователя по токену
     * @return email
     */
    String getEmailClaim();

    /**
     * Возврашает номер телефона пользователя по токену
     * @return номер телефона
     */
    String getPhoneNumberClaim();
    /**
     * Возврашает логин пользователя по токену
     * @return логин
     */
    String getPreferredUsernameClaim();
    /**
     * Возврашает день рождения пользователя по токену
     * @return день рождения
     */
    String getDateBirthdayClaim();

    String getGroupIdClaim();

    String getFirstNameClaim();
    String getMiddleNameClaim();
    String getLastNameClaim();

    /**
     * Возвращает токен пользователя
     * @return токен
     */
    Jwt getJwtSecurityContext();

}
