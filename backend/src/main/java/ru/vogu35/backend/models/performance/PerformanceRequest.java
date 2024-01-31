package ru.vogu35.backend.models.performance;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceRequest {
    @NotBlank
    private String subjectName;
    @NotBlank
    private String groupName;
}
