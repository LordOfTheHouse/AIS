package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.Lesson;
import ru.vogu35.backend.entities.SubjectGroup;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    long save(Lesson lesson);
    boolean createLecture(SubjectGroup subjectGroup, String groupName, String topic);
    List<Lesson> findAllByTodayAndSubjectGroupId(long subjectGroupId);
    List<Lesson> findAllByDateEvent(LocalDate date, long subjectGroupId);
    List<Lesson> findAllBySubjectGroupId(long subjectGroupId);

}
