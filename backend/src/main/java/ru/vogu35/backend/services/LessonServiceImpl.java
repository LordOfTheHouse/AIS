package ru.vogu35.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.StudentLesson;
import ru.vogu35.backend.repositories.LessonRepository;
import ru.vogu35.backend.services.auth.JwtService;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, JwtService jwtService) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public long save(StudentLesson studentLesson) {
        return lessonRepository.save(studentLesson).getId();
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
    public boolean update(StudentLesson studentLesson) {
        if(lessonRepository.existsById(studentLesson.getId())){
            lessonRepository.save(studentLesson);
            return true;
        }
        return false;
    }

    @Override
    public List<StudentLesson> findByStudentId(String id) {
        if(lessonRepository.existsByStudentId(id)){
            return lessonRepository.findAllByStudentId(id);
        }
        return List.of();
    }
}
