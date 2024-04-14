package ru.vogu35.backend.entities.user;

import ru.vogu35.backend.models.auth.UserResponse;

public interface UserBuilder {
    UserResponse build();
    UserBuilder email(String email);
    UserBuilder username(String username);
    UserBuilder id(String id);
    UserBuilder firstName(String firstName);
    UserBuilder middleName(String middleName);
    UserBuilder lastName(String lastName);
    UserBuilder groupName(String groupName);
}
