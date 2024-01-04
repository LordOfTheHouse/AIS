package ru.vogu35.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vogu35.backend.entities.SubjectGroup;

import java.util.List;

@Repository
public interface SubjectGroupRepository extends JpaRepository<SubjectGroup, Long> {
    List<SubjectGroup> findAllByGroup_Id(long id);
    List<SubjectGroup> findAllByTeacherId(String id);
}
