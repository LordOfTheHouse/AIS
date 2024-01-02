package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.Lesson;

import java.util.List;

public interface LessonService {
    long save(Lesson lesson);
    boolean deleteById(long id);
    boolean update(Lesson lesson);
    List<Lesson> findByStudentId(String id);
}
