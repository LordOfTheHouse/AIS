package ru.vogu35.backend.services;

import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Lesson;
import ru.vogu35.backend.repositories.LessonRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public long save(Lesson lesson) {
        return lessonRepository.save(lesson).getId();
    }

    @Override
    public List<Lesson> findAllByToday(LocalDate date) {
        return null;
    }

    @Override
    public List<Lesson> findAllByDateEvent(LocalDate date) {
        return lessonRepository.findAllByDateEvent(date);
    }
}
