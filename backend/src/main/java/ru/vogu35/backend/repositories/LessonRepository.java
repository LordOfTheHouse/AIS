package ru.vogu35.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vogu35.backend.entities.StudentLesson;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<StudentLesson, Long> {
    boolean existsByStudentId(String id);
    List<StudentLesson> findAllByStudentId(String id);
}
