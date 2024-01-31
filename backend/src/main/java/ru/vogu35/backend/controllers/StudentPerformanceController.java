package ru.vogu35.backend.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.entities.Lesson;
import ru.vogu35.backend.entities.StudentLesson;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.entities.enums.EInstitute;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.performance.LessonPerformanceModel;
import ru.vogu35.backend.models.performance.PerformanceRequest;
import ru.vogu35.backend.models.SubjectModel;
import ru.vogu35.backend.models.performance.StudentPerformanceModel;
import ru.vogu35.backend.proxies.KeycloakApiProxy;
import ru.vogu35.backend.services.LessonService;
import ru.vogu35.backend.services.StudentLessonService;
import ru.vogu35.backend.services.SubjectGroupService;
import ru.vogu35.backend.services.auth.JwtService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/perfomance")
public class StudentPerformanceController {

    private final SubjectGroupService subjectGroupService;
    private final LessonService lessonService;
    private final StudentLessonService studentLessonService;

    @Autowired
    public StudentPerformanceController(SubjectGroupService subjectGroupService, LessonService lessonService,
                                        StudentLessonService studentLessonService) {
        this.subjectGroupService = subjectGroupService;
        this.lessonService = lessonService;
        this.studentLessonService = studentLessonService;

    }

    @PreAuthorize("hasRole('client_teacher')")
    @GetMapping("/groups")
    public ResponseEntity<List<GroupModel>> getGroups(@RequestParam(required = false) String institute) {
        List<GroupModel> groups;
        if (institute != null) {
            groups = subjectGroupService.findGroupTeacherByInstitute(institute);
            return ResponseEntity.ok(groups);
        }
        groups = subjectGroupService.findGroupByTeacherId();
        if (groups.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    @PreAuthorize("hasRole('client_teacher')")
    @GetMapping("/institute")
    public ResponseEntity<List<EInstitute>> getInstitute() {
        return ResponseEntity.ok(List.of(EInstitute.values()));
    }

    @PreAuthorize("hasRole('client_teacher')")
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectModel>> getSubjects(@RequestParam(required = false) @NotBlank String groupName) {
        List<SubjectModel> subjects;
        if (groupName != null) {
            subjects = subjectGroupService.findSubjectTeacherByGroup(groupName);
            return ResponseEntity.ok(subjects);
        }
        subjects = subjectGroupService.findSubjectByTeacherId();
        if (subjects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subjects);
    }

    @PreAuthorize("hasRole('client_teacher')")
    @GetMapping
    public ResponseEntity<List<LessonPerformanceModel>> getPerformanceStudent(@RequestBody PerformanceRequest performanceRequest) {
        List<SubjectGroup> subjectGroups = subjectGroupService.findSubjectTeacherByGroupAndSubject(performanceRequest.getGroupName(),
                performanceRequest.getSubjectName());
        List<Lesson> lessons = subjectGroups.stream()
                .flatMap(subjectGroup -> lessonService.findAllBySubjectGroupId(subjectGroup.getId()).stream())
                .toList();
        List<LessonPerformanceModel> lessonPerformanceModels = lessons.stream()
                .map(lesson -> {
                    List<StudentPerformanceModel> studentsLesson = studentLessonService.findByLessonId(lesson.getId())
                            .stream()
                            .map(StudentPerformanceModel::new)
                            .toList();
                    var lessonPerformanceModel = new LessonPerformanceModel();
                    lessonPerformanceModel.setIdLesson(lesson.getId());
                    lessonPerformanceModel.setDate(lesson.getDateEvent());
                    lessonPerformanceModel.setTypeSubject(lesson.getSubjectGroup().getSubject().getTypeSubject());
                    lessonPerformanceModel.setStudentMarksList(studentsLesson);
                    return lessonPerformanceModel;
                })
                .toList();
        if(lessonPerformanceModels.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lessonPerformanceModels);
    }
}
