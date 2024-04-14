package ru.vogu35.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartLecture {
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startLecture;
    private String topic;
    private String groupName;
}
