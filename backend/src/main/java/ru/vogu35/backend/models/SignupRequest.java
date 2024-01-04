package ru.vogu35.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String firstName;
    private String middleName;
    private String secondName;
    private String groupName;
    private String email;
    private String password;
}
