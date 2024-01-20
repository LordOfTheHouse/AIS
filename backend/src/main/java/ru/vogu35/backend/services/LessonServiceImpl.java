package ru.vogu35.backend.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Lesson;
import ru.vogu35.backend.entities.StudentLesson;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.UserInfo;
import ru.vogu35.backend.models.UserResponse;
import ru.vogu35.backend.proxies.KeycloakApiProxy;
import ru.vogu35.backend.repositories.LessonRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final StudentLessonService studentLessonService;
    private final KeycloakApiProxy keycloakApiProxy;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, StudentLessonService studentLessonService,
                                                KeycloakApiProxy keycloakApiProxy) {
        this.lessonRepository = lessonRepository;
        this.studentLessonService = studentLessonService;
        this.keycloakApiProxy = keycloakApiProxy;
    }

    @Override
    public long save(Lesson lesson) {
        return lessonRepository.save(lesson).getId();
    }

    @Transactional
    @Override
    public boolean createLecture(SubjectGroup subjectGroup, String groupName, String topic) {

        Lesson lesson = new Lesson();
        lesson.setDateEvent(LocalDate.now());
        lesson.setTopic(topic);
        lesson.setSubjectGroup(subjectGroup);
        log.info("create lecture {}", lesson);
        Lesson finalLesson = lessonRepository.save(lesson);

        List<UserResponse> users = keycloakApiProxy.findUserByGroup(groupName);

        users.stream().map(user ->{
            log.info("create lesson student {}", user);
            StudentLesson studentLesson = new StudentLesson();
            studentLesson.setStudentId(user.getId());
            studentLesson.setLesson(finalLesson);
            studentLesson.setIsPresent(false);
            return studentLessonService.save(studentLesson);
        }).forEach((it)->{});
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
}
