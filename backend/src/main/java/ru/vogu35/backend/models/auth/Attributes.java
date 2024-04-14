package ru.vogu35.backend.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Attributes {
    private String groupName;
    private String middleName;
}
