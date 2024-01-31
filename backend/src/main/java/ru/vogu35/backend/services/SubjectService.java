package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.Subject;

import java.util.Optional;

public interface SubjectService {
    long save(Subject subject);
    boolean update(Subject subject);
    boolean deleteById(long id);
    Optional<Subject> findById(long id);
}
