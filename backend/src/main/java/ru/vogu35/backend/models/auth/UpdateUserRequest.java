package ru.vogu35.backend.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String picture;
    private Attributes attributes;
}
