package ru.vogu35.backend.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Group;
import ru.vogu35.backend.repositories.GroupRepository;
import ru.vogu35.backend.services.GroupService;

import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public long save(Group group) {
        return groupRepository.save(group).getId();
    }

    @Override
    public boolean deleteById(long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Group group) {
        if (groupRepository.existsById(group.getId())) {
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Group> findById(long id) {
        return groupRepository.findById(id);
    }
}
