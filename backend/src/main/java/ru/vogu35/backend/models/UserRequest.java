package ru.vogu35.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private List<Credential> credentials;
    private Attributes attributes;
}
