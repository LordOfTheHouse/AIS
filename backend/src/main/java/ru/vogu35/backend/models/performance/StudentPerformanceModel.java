package ru.vogu35.backend.models.performance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.entities.StudentLesson;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPerformanceModel {
    private String studentId;
    private Integer mark;
    private boolean isPresent;

    public StudentPerformanceModel(StudentLesson studentLesson) {
        this.studentId = studentLesson.getStudentId();
        this.mark = studentLesson.getMark();
        this.isPresent = studentLesson.getIsPresent();
    }

}
