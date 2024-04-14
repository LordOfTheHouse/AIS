package ru.vogu35.backend.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.entities.user.UserBuilder;
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


    public static class UserResponseBuilder implements UserBuilder {
        private final UserResponse user;

        public UserResponseBuilder(String id){
            user = new UserResponse();
            user.id = id;
        }

        @Override
        public UserResponse build() {
            return user;
        }

        @Override
        public UserBuilder email(String email) {
            user.email = email;
            return this;
        }

        @Override
        public UserBuilder username(String username) {
            user.username = username;
            return this;
        }

        @Override
        public UserBuilder id(String id) {
            user.id = id;
            return this;
        }


        @Override
        public UserBuilder firstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        @Override
        public UserBuilder middleName(String middleName) {
            user.middleName = middleName;
            return this;
        }

        @Override
        public UserBuilder lastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        @Override
        public UserBuilder groupName(String groupName) {
            user.groupName = groupName;
            return this;
        }

    }

}
