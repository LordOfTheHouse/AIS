package ru.vogu35.backend.repositories;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vogu35.backend.entities.StudentLesson;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentLessonRepository extends JpaRepository<StudentLesson, Long> {
    boolean existsByStudentId(String id);
    List<StudentLesson> findAllByStudentId(String id);

    Optional<StudentLesson> findByStudentIdAndLesson_Id(@NotBlank String studentId, long lesson_id);
}
