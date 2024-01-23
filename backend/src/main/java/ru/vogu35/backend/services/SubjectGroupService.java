package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;
import ru.vogu35.backend.models.schedule.ScheduleModel;
import ru.vogu35.backend.models.schedule.ScheduleTodayModel;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Прорабатывает бизнес логику для работы с расписанием
 */
public interface SubjectGroupService {
    long save(SubjectGroup subjectGroup);
    boolean update(SubjectGroup subjectGroup);
    boolean deleteById(long id);
    List<List<ScheduleModel>> findAllByGroupId(boolean isEvenWeek);
    List<List<ScheduleModel>> findAllByTeacherId(boolean isEvenWeek);
    List<GroupModel> findGroupByTeacherId();
    List<GroupModel> findGroupTeacherByInstitute(String institute);
    Optional<SubjectGroup> findStartLecture(String groupName, LocalTime startLecture);
    List<ScheduleTodayModel> findAllTodayByTeacher();
    List<SubjectModel> findSubjectByTeacherId();
    List<SubjectModel> findSubjectTeacherByGroup(String groupName);
}
