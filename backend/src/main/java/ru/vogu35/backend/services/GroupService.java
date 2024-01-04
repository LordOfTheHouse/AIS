package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.Group;

import java.util.Optional;

public interface GroupService {
    long save(Group group);
    boolean deleteById(long id);
    boolean update(Group group);
    Optional<Group> findById(long id);
}
