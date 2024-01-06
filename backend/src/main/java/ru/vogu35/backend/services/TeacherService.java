package ru.vogu35.backend.services;

import ru.vogu35.backend.entities.Teacher;

import java.util.Optional;

public interface TeacherService {
    String save(Teacher teacher);
    boolean deleteById(String id);
    Optional<Teacher> findById(String id);
}
