package ru.vogu35.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vogu35.backend.entities.Group;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.entities.enums.EInstitute;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;

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

    List<SubjectGroup> findAllByTeacherIdAndGroup_Institute(String teacherId, EInstitute institute);
    default List<GroupModel> findGroupTeacherByInstitute(String teacherId, EInstitute institute) {
        return findAllByTeacherIdAndGroup_Institute(teacherId, institute)
                .stream().map(subjectGroup->
                new GroupModel(subjectGroup.getGroup().getId(), subjectGroup.getGroup().getName()))
                .distinct()
                .toList();
    }

    List<SubjectGroup> findAllByTeacherIdAndGroup_Name(String teacherId, String groupName);

    default List<SubjectModel> findGroupTeacherByGroup(String teacherId, String groupName){
        return findAllByTeacherIdAndGroup_Name(teacherId, groupName).stream()
                .map(subjectGroup -> new SubjectModel(subjectGroup.getSubject().getId(),
                                                            subjectGroup.getSubject().getTitle()))
                .distinct()
                .toList();
    }

    List<SubjectGroup> findAllByTeacherIdAndGroup_NameAndAndSubject_Title(String teacherId, String groupName,
                                                                            String subjectName);
}
