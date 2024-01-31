package ru.vogu35.backend.models.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.entities.SubjectGroup;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTodayModel {
    private String nameSubject;
    private LocalTime start;
    private String groupName;
    private String typeSubject;
    private String topic;
    private long idLesson;

    public ScheduleTodayModel(SubjectGroup subjectGroup, String topic, long idLesson) {
        this.nameSubject = subjectGroup.getSubject().getTitle();
        this.start = subjectGroup.getStart();
        this.groupName = subjectGroup.getGroup().getName();
        this.typeSubject = subjectGroup.getSubject().getTypeSubject().name();
        this.topic = topic;
        this.idLesson = idLesson;
    }
}
