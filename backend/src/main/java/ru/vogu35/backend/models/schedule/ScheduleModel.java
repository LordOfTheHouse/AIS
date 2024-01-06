package ru.vogu35.backend.models.schedule;

import ru.vogu35.backend.entities.SubjectGroup;

import java.time.LocalTime;

public class ScheduleModel {
    private String nameSubject;
    private LocalTime start;
    private String groupName;
    private String teacherName;

    public ScheduleModel(SubjectGroup subjectGroup, String teacherName) {
        this.nameSubject = subjectGroup.getSubject().getTitle();
        this.start = subjectGroup.getStart();
        this.groupName = subjectGroup.getGroup().getName();
        this.teacherName = teacherName;
    }
}
