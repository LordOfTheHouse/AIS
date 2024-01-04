package ru.vogu35.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Group;
import ru.vogu35.backend.entities.Subject;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;
import ru.vogu35.backend.repositories.SubjectGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectGroupServiceImpl implements SubjectGroupService {

    private final SubjectGroupRepository subjectGroupRepository;
    private final JwtService jwtService;
    private final SubjectService subjectService;
    private final GroupService groupService;

    @Autowired
    public SubjectGroupServiceImpl(SubjectGroupRepository subjectGroupRepository, JwtService jwtService,
                                   SubjectService subjectService, GroupService groupService) {
        this.subjectGroupRepository = subjectGroupRepository;
        this.jwtService = jwtService;
        this.subjectService = subjectService;
        this.groupService = groupService;
    }

    @Override
    public long save(SubjectGroup subjectGroup) {
        return subjectGroupRepository.save(subjectGroup).getId();
    }

    @Override
    public boolean update(SubjectGroup subjectGroup) {
        if (subjectGroupRepository.existsById(subjectGroup.getId())) {
            subjectGroupRepository.save(subjectGroup);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        if (subjectGroupRepository.existsById(id)) {
            subjectGroupRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<SubjectGroup> findAllByGroupId() {
        return subjectGroupRepository.findAllByGroup_Id(Long.parseLong(jwtService.getGroupIdClaim()));
    }

    @Override
    public List<SubjectGroup> findAllByTeacherId() {
        return subjectGroupRepository.findAllByTeacherId(jwtService.getSubClaim());
    }

    @Override
    public List<GroupModel> findGroupByTeacherId() {
        List<SubjectGroup> subjectGroups = findAllByTeacherId();
        return subjectGroups.stream()
                .map(subjectGroup -> {
                            Optional<Group> group = groupService.findById(subjectGroup.getGroup().getId());
                            return group.map(subject -> new GroupModel(subject.getId(), subject.getName())).orElseGet(GroupModel::new);
                        }
                )
                .distinct()
                .toList();
    }

    @Override
    public List<SubjectModel> findSubjectByTeacherId() {
        List<SubjectGroup> subjectGroups = findAllByTeacherId();
        return subjectGroups.stream()
                .map(subjectGroup -> {
                            Optional<Subject> subject = subjectService.findById(subjectGroup.getSubject().getId());
                            return subject.map(sub -> new SubjectModel(sub.getId(), sub.getTitle())).orElseGet(SubjectModel::new);
                        }
                )
                .distinct()
                .toList();
    }
}
