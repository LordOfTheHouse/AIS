package ru.vogu35.backend.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String groupName;
    private String email;
    private String password;
}
