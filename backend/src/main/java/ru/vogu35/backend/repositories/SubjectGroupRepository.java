package ru.vogu35.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vogu35.backend.entities.SubjectGroup;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectGroupRepository extends JpaRepository<SubjectGroup, Long> {
    List<SubjectGroup> findAllByGroup_Id(long id);
    List<SubjectGroup> findAllByTeacherId(String id);
    List<SubjectGroup> findAllByTeacherIdAndWeekdayAndWeekEven(String id, long weekday, boolean even);
    List<SubjectGroup> findAllByGroup_NameAndWeekdayAndWeekEven(String name, long weekday, boolean even);

    Optional<SubjectGroup> findAllByTeacherIdAndGroup_NameAndWeekdayAndWeekEvenAndStart(String id, String groupName,
                                                                                         int weekday, boolean even, LocalTime start);
}
