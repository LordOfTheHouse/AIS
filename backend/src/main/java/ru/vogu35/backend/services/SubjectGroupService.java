package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;
import ru.vogu35.backend.models.schedule.ScheduleModel;

import java.util.List;
import java.util.Map;

public interface SubjectGroupService {
    long save(SubjectGroup subjectGroup);
    boolean update(SubjectGroup subjectGroup);
    boolean deleteById(long id);
    List<List<ScheduleModel>> findAllByGroupId();
    List<List<ScheduleModel>> findAllByTeacherId();
    List<GroupModel> findGroupByTeacherId();
    List<SubjectModel> findSubjectByTeacherId();
}
