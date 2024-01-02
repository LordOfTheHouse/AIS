package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;

import java.util.List;

public interface SubjectGroupRepository {
    long save(SubjectGroup subjectGroup);
    boolean update(SubjectGroup subjectGroup);
    boolean deleteById(long id);
    List<SubjectGroup> findAllByGroupId(long id);
    List<SubjectGroup> findAllByTeacherId(String id);
    List<GroupModel> findGroupByTeacherId(String id);
    List<SubjectModel> findSubjectByUserId(String id);
}
