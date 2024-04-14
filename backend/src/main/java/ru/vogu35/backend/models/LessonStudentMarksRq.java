package ru.vogu35.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

public record LessonStudentMarksRq(Long idLecture, String groupName) implements Serializable {
    public LessonStudentMarksRq(@JsonProperty(value = "idLecture") Long idLecture,
                                @JsonProperty(value = "groupName") String groupName) {
        this.idLecture = idLecture;
        this.groupName = groupName;
    }
}
