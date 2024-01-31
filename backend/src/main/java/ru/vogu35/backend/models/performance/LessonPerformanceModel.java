package ru.vogu35.backend.models.performance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.entities.enums.ETypeSubject;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonPerformanceModel {
    private long idLesson;
    private LocalDate date;
    private List<StudentPerformanceModel> studentMarksList;
    private ETypeSubject typeSubject;
}
