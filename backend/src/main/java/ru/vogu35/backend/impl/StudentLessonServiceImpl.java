package ru.vogu35.backend.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.StudentLesson;
import ru.vogu35.backend.repositories.StudentLessonRepository;
import ru.vogu35.backend.services.StudentLessonService;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentLessonServiceImpl implements StudentLessonService {
    private final StudentLessonRepository studentLessonRepository;

    @Autowired
    public StudentLessonServiceImpl(StudentLessonRepository studentLessonRepository) {
        this.studentLessonRepository = studentLessonRepository;
    }

    @Override
    public long save(StudentLesson studentLesson) {
        log.info("save studentLesson {}", studentLesson);
        return studentLessonRepository.save(studentLesson).getId();
    }

    @Override
    public boolean deleteById(long id) {
        if(studentLessonRepository.existsById(id)){
            studentLessonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(StudentLesson studentLesson) {
        if(studentLessonRepository.existsById(studentLesson.getId())){
            studentLessonRepository.save(studentLesson);
            return true;
        }
        return false;
    }

    @Override
    public List<StudentLesson> findByStudentId(String id) {
        if(studentLessonRepository.existsByStudentId(id)){
            return studentLessonRepository.findAllByStudentId(id);
        }
        return List.of();
    }

    @Override
    public List<StudentLesson> findByLessonId(long id) {
        return studentLessonRepository.findAllByLesson_Id(id);
    }

    @Override
    public Optional<StudentLesson> findByStudentIdAndLessonId(String studentId, long lessonId) {
        return studentLessonRepository.findByStudentIdAndLesson_Id(studentId, lessonId);
    }
}
