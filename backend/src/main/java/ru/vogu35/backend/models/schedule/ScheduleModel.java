package ru.vogu35.backend.models.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.entities.SubjectGroup;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleModel {
    private String nameSubject;
    private LocalTime start;
    private String classroom;
    private String groupName;
    private String teacherName;
    private String typeSubject;

    public ScheduleModel(SubjectGroup subjectGroup, String teacherName) {
        this.nameSubject = subjectGroup.getSubject().getTitle();
        this.start = subjectGroup.getStart();
        this.groupName = subjectGroup.getGroup().getName();
        this.teacherName = teacherName;
        this.classroom = subjectGroup.getClassroom();
        this.typeSubject = subjectGroup.getSubject().getTypeSubject().name();
    }
}
