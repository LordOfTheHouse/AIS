package ru.vogu35.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vogu35.backend.entities.Lesson;
import ru.vogu35.backend.entities.SubjectGroup;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByDateEventAndSubjectGroup_Id(LocalDate localDate, long subject_id);
}
