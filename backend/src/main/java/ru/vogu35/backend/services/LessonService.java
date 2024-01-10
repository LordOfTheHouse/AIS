package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.StudentLesson;

import java.util.List;

public interface LessonService {
    long save(StudentLesson studentLesson);
    boolean deleteById(long id);
    boolean update(StudentLesson studentLesson);
    List<StudentLesson> findByStudentId(String id);
}
