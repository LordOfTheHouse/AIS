package ru.vogu35.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Teacher;
import ru.vogu35.backend.repositories.TeacherRepository;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public String save(Teacher teacher) {
        return teacherRepository.save(teacher).getId();
    }

    @Override
    public boolean deleteById(String id) {
        if(teacherRepository.existsById(id)){
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Teacher> findById(String id) {
        return teacherRepository.findById(id);
    }
}
