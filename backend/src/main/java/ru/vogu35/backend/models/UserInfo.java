package ru.vogu35.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private AttributesUserInfo attributes;
}
