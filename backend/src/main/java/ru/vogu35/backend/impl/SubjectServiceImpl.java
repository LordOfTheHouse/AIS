package ru.vogu35.backend.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Subject;
import ru.vogu35.backend.repositories.SubjectRepository;
import ru.vogu35.backend.services.SubjectService;

import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public long save(Subject subject) {
        return subjectRepository.save(subject).getId();
    }

    @Override
    public boolean update(Subject subject) {
        if(subjectRepository.existsById(subject.getId())){
            subjectRepository.save(subject);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        if(subjectRepository.existsById(id)){
            subjectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Subject> findById(long id) {
        return subjectRepository.findById(id);
    }
}
