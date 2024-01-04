package ru.vogu35.backend.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import ru.vogu35.backend.exseptions.UserNotFoundException;

/**
 * Реализует логику работы с токеном пользователя
 */
@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String getSubClaim() {
        String claimName = JwtClaimNames.SUB;
        return getJwtSecurityContext().getClaim(claimName);
    }

    @Override
    public String getEmailClaim() {
        return getJwtSecurityContext().getClaim("email");
    }

    @Override
    public String getPhoneNumberClaim() {
        return getJwtSecurityContext().getClaim("phone_number");
    }

    @Override
    public String getPreferredUsernameClaim() {
        return getJwtSecurityContext().getClaim("preferred_username");
    }

    @Override
    public String getDateBirthdayClaim() {
        return getJwtSecurityContext().getClaim("date_birthday");
    }

    @Override
    public String getGroupIdClaim() {
        return getJwtSecurityContext().getClaim("group_id");
    }

    @Override
    public Jwt getJwtSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuthenticationToken.getToken();
            return jwt;
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}
