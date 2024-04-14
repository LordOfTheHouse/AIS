package ru.vogu35.backend.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.entities.StudentLesson;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.LessonStudentMarksRq;
import ru.vogu35.backend.models.StartLecture;
import ru.vogu35.backend.models.auth.UserResponse;
import ru.vogu35.backend.models.performance.StudentPerformanceModel;
import ru.vogu35.backend.models.schedule.ScheduleTodayModel;
import ru.vogu35.backend.proxies.AuthService;
import ru.vogu35.backend.services.LessonService;
import ru.vogu35.backend.services.StudentLessonService;
import ru.vogu35.backend.services.SubjectGroupService;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/lecture")
public class LectureController {

    private final AuthService authService;
    private final SubjectGroupService subjectGroupService;
    private final LessonService lessonService;
    private final StudentLessonService studentLessonService;


    public LectureController(AuthService authService, SubjectGroupService subjectGroupService,
                             LessonService lessonService, StudentLessonService studentLessonService) {
        this.authService = authService;
        this.subjectGroupService = subjectGroupService;
        this.lessonService = lessonService;
        this.studentLessonService = studentLessonService;
    }

    @PreAuthorize("hasAnyRole('client_student', 'client_teacher')")
    @GetMapping("/groups/today")
    public ResponseEntity<List<ScheduleTodayModel>> getGroups() {
        return ResponseEntity.ok(subjectGroupService.findAllTodayByTeacher());
    }

    @PreAuthorize("hasAnyRole('client_student', 'client_teacher')")
    @GetMapping("/{group}")
    public ResponseEntity<List<UserResponse>> getStudentGroup(@PathVariable("group") String groupName) {
        log.info("students group: {}", groupName);
        return ResponseEntity.ok(authService.findUserByGroup(groupName));
    }

    @PreAuthorize("hasRole('client_teacher')")
    @PostMapping("/start")
    public ResponseEntity<?> startLecture(@RequestBody StartLecture startLecture) {
        log.info("Create LESSON: {}", startLecture);
        Optional<SubjectGroup> subjectGroups = subjectGroupService.findStartLecture(startLecture.getGroupName(),
                startLecture.getStartLecture());
        if (subjectGroups.isEmpty()) {
            return ResponseEntity.badRequest().body("Предмет или  группа не определена");
        }
        if (lessonService.createLecture(subjectGroups.get(), startLecture.getGroupName(), startLecture.getTopic())) {
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('client_teacher')")
    @PostMapping("/getStudentMark")
    public ResponseEntity<?> getStudentMark(@RequestBody LessonStudentMarksRq studentLecture) {
        log.info("getStudentMark: {}", studentLecture);
        List<UserResponse> users = authService.findUserByGroup(studentLecture.groupName());
        List<StudentPerformanceModel> studentsPerformance = users.stream()
                .map( user -> studentLessonService.findByStudentIdAndLessonId(
                                        user.getId(),
                                        studentLecture.idLecture() ) )
                .filter(Optional::isPresent)
                .map(lesson -> {
                    StudentLesson less = lesson.get();
                    UserResponse user = users.stream()
                            .filter(userRes -> userRes.getId().equals(less.getStudentId()))
                            .findFirst()
                            .get();
                    String name = user.getLastName() + " " + user.getFirstName() + " " + user.getMiddleName();
                    return new StudentPerformanceModel(less.getStudentId(), name, less.getMark(), less.getIsPresent());
                })
                .toList();

        if (studentsPerformance.isEmpty()) {
            return ResponseEntity.badRequest().body("Студенты не найден");
        }
        return ResponseEntity.ok().body(studentsPerformance);
    }

    @PreAuthorize("hasRole('client_teacher')")
    @PutMapping("/{idLesson}/groups/{idStudent}/present")
    public ResponseEntity<?> updatePresentStudent(@PathVariable @NotBlank String idStudent,
                                                  @PathVariable @NotBlank long idLesson) {
        log.info("update present");
        Optional<StudentLesson> studentLesson = studentLessonService.findByStudentIdAndLessonId(idStudent, idLesson);
        if (studentLesson.isPresent()) {
            if (studentLesson.get().getIsPresent() != null) {
                studentLesson.get().setIsPresent(!studentLesson.get().getIsPresent());
            }
            studentLessonService.save(studentLesson.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('client_teacher')")
    @PutMapping("{idLesson}/groups/{idStudent}")
    public ResponseEntity<?> updateMarkStudent(@PathVariable("idStudent") @NotBlank String idStudent,
                                               @PathVariable("idLesson") @NotBlank long idLesson,
                                               @RequestParam(name = "mark") int mark) {
        log.info("update mark");
        Optional<StudentLesson> studentLesson = studentLessonService.findByStudentIdAndLessonId(idStudent, idLesson);
        if (studentLesson.isPresent()) {

            if (mark >= 0 && mark <= 100) {
                studentLesson.get().setMark(mark);
                studentLessonService.save(studentLesson.get());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
