package ru.vogu35.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vogu35.backend.entities.SubjectGroup;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface SubjectGroupRepository extends JpaRepository<SubjectGroup, Long> {
    List<SubjectGroup> findAllByGroup_Id(long id);
    List<SubjectGroup> findAllByTeacherId(String id);
    List<SubjectGroup> findAllByTeacherIdAndWeekdayAndWeekEven(String id, long weekday, boolean even);
    List<SubjectGroup> findAllByGroup_NameAndWeekdayAndWeekEven(String name, long weekday, boolean even);
    List<SubjectGroup> findAllTeacherIdByGroup_NameAndWeekdayAndStart(String id, String groupName, int weekday, LocalTime start);
}
