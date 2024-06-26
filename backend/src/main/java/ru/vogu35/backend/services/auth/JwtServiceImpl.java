package ru.vogu35.backend.services.auth;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        return getJwtSecurityContext().getClaim("group_name");
    }

    @Override
    public String getFirstNameClaim() {
        return getJwtSecurityContext().getClaim("given_name");
    }

    @Override
    public String getMiddleNameClaim() {
        return getJwtSecurityContext().getClaim("middle_name");
    }

    @Override
    public String getLastNameClaim() {
        return getJwtSecurityContext().getClaim("family_name");
    }

    @Override
    public String getPictureClaim() {
        return getJwtSecurityContext().getClaim("picture");
    }

    @Override
    public Jwt getJwtSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuthenticationToken.getToken();
            log.info("claims: {}", jwt.getClaims());
            return jwt;
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}
