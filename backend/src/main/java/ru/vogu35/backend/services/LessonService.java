package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.Lesson;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    long save(Lesson lesson);
    List<Lesson> findAllByToday(LocalDate date);
    List<Lesson> findAllByDateEvent(LocalDate date);

}
