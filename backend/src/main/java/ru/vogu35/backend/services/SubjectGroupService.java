package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;

import java.util.List;

public interface SubjectGroupService {
    long save(SubjectGroup subjectGroup);
    boolean update(SubjectGroup subjectGroup);
    boolean deleteById(long id);
    List<SubjectGroup> findAllByGroupId();
    List<SubjectGroup> findAllByTeacherId();
    List<GroupModel> findGroupByTeacherId();
    List<SubjectModel> findSubjectByTeacherId();
}
