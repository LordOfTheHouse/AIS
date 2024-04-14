package ru.vogu35.backend.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Lesson;
import ru.vogu35.backend.entities.StudentLesson;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.auth.UserResponse;
import ru.vogu35.backend.proxies.AuthService;
import ru.vogu35.backend.repositories.LessonRepository;
import ru.vogu35.backend.services.LessonService;
import ru.vogu35.backend.services.StudentLessonService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final StudentLessonService studentLessonService;
    private final AuthService authService;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, StudentLessonService studentLessonService,
                                                AuthService authService) {
        this.lessonRepository = lessonRepository;
        this.studentLessonService = studentLessonService;
        this.authService = authService;
    }

    @Override
    public long save(Lesson lesson) {
        return lessonRepository.save(lesson).getId();
    }

    @Transactional
    @Override
    public boolean createLecture(SubjectGroup subjectGroup, String groupName, String topic) {

        List<Lesson> lessons = lessonRepository.
                findAllByDateEventAndSubjectGroup_Id(LocalDate.now(), subjectGroup.getId());
        Lesson lesson;
        if(lessons.isEmpty()) {
            lesson = new Lesson();
            lesson.setDateEvent(LocalDate.now());
            lesson.setTopic(topic);
            lesson.setSubjectGroup(subjectGroup);
        } else {
            lesson = lessons.get(0);
            lesson.setTopic(topic);
        }
        log.info("create lecture {}", lesson);

        Lesson finalLesson = lessonRepository.save(lesson);
        if( lessons.isEmpty() ) {
            List<UserResponse> users = authService.findUserByGroup(groupName);
            users.stream().map(user -> {
                StudentLesson studentLesson = new StudentLesson();
                studentLesson.setStudentId(user.getId());
                studentLesson.setLesson(finalLesson);
                studentLesson.setMark(1);
                studentLesson.setIsPresent(false);
                return studentLessonService.save(studentLesson);
            }).forEach((it)->{});
        }



        return true;
    }

    @Override
    public List<Lesson> findAllByTodayAndSubjectGroupId(long subjectGroupId) {
        return findAllByDateEvent(LocalDate.now(), subjectGroupId);
    }

    @Override
    public List<Lesson> findAllByDateEvent(LocalDate date, long subjectGroupId) {
        return lessonRepository.findAllByDateEventAndSubjectGroup_Id(date, subjectGroupId);
    }

    @Override
    public List<Lesson> findAllBySubjectGroupId(long subjectGroupId) {
        return lessonRepository.findAllBySubjectGroup_Id(subjectGroupId);
    }
}
