package ru.vogu35.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String groupName;

    public UserResponse(UserInfo userInfo) {
        id = userInfo.getId();
        username = userInfo.getUsername();
        email = userInfo.getEmail();
        firstName = userInfo.getFirstName();
        lastName = userInfo.getLastName();
        middleName = userInfo.getAttributes().getMiddleName().get(0);
        groupName = userInfo.getAttributes().getGroupName().get(0);
    }
}