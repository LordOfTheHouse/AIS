package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.StudentLesson;

import java.util.List;
import java.util.Optional;

public interface StudentLessonService {
    long save(StudentLesson studentLesson);
    boolean deleteById(long id);
    boolean update(StudentLesson studentLesson);
    List<StudentLesson> findByStudentId(String id);
    Optional<StudentLesson> findByStudentIdAndLessonId(String studentId, long lessonId);
}
