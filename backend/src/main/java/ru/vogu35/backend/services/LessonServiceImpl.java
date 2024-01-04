package ru.vogu35.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Lesson;
import ru.vogu35.backend.repositories.LessonRepository;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, JwtService jwtService) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public long save(Lesson lesson) {
        return lessonRepository.save(lesson).getId();
    }

    @Override
    public boolean deleteById(long id) {
        if(lessonRepository.existsById(id)){
            lessonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Lesson lesson) {
        if(lessonRepository.existsById(lesson.getId())){
            lessonRepository.save(lesson);
            return true;
        }
        return false;
    }

    @Override
    public List<Lesson> findByStudentId(String id) {
        if(lessonRepository.existsByStudentId(id)){
            return lessonRepository.findAllByStudentId(id);
        }
        return List.of();
    }
}
