package ru.vogu35.backend.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AttributesUserInfo {
    private List<String> groupName;
    private List<String> middleName;
}
