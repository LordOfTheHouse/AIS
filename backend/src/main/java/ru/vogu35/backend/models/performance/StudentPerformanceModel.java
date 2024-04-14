package ru.vogu35.backend.models.performance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.entities.StudentLesson;

@Data
@NoArgsConstructor
public class StudentPerformanceModel {
    private String studentId;
    private String studentName;
    private Integer mark;
    private boolean isPresent;

    public StudentPerformanceModel(StudentLesson studentLesson) {
        this.studentId = studentLesson.getStudentId();
        this.mark = studentLesson.getMark();
        this.isPresent = studentLesson.getIsPresent();
    }

    public StudentPerformanceModel( @JsonProperty("studentId") String studentId,
                                   @JsonProperty("studentName") String studentName,
                                   @JsonProperty("mark") Integer mark,
                                   @JsonProperty("isPresent") boolean isPresent) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.mark = mark;
        this.isPresent = isPresent;
    }
}
