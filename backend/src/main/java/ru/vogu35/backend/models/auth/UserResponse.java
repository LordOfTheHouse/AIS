package ru.vogu35.backend.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.models.UserInfo;

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
    private String picture;

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
