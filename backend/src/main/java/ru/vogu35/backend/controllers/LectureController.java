package ru.vogu35.backend.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.auth.JwtService;
import ru.vogu35.backend.entities.StudentLesson;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.exseptions.UserNotFoundException;
import ru.vogu35.backend.models.LessonStudentMarksRq;
import ru.vogu35.backend.models.StartLecture;
import ru.vogu35.backend.models.auth.UserResponse;
import ru.vogu35.backend.models.metrics.Metric;
import ru.vogu35.backend.models.performance.StudentPerformanceModel;
import ru.vogu35.backend.models.schedule.ScheduleTodayModel;
import ru.vogu35.backend.proxies.AuthService;
import ru.vogu35.backend.services.LessonService;
import ru.vogu35.backend.services.StudentLessonService;
import ru.vogu35.backend.services.SubjectGroupService;

import java.time.LocalDateTime;
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
    private final JwtService jwtService;

    public LectureController(AuthService authService, SubjectGroupService subjectGroupService,
                             LessonService lessonService, StudentLessonService studentLessonService, JwtService jwtService) {
        this.authService = authService;
        this.subjectGroupService = subjectGroupService;
        this.lessonService = lessonService;
        this.studentLessonService = studentLessonService;
        this.jwtService = jwtService;
    }

    @PreAuthorize("hasAnyRole('client_student', 'client_teacher')")
    @GetMapping("/groups/today")
    public ResponseEntity<List<ScheduleTodayModel>> getGroups() {
        if( jwtService.getGroupIdClaim().equals("Teacher") ) {
            return ResponseEntity.ok(subjectGroupService.findAllTodayByTeacher());
        }
        return ResponseEntity.ok(subjectGroupService.findAllTodayByGroupId());
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

    @PreAuthorize("hasAnyRole('client_student', 'client_teacher')")
    @PostMapping("/getStudentMark")
    public ResponseEntity<List<StudentPerformanceModel>> fetchStudentMarks(@RequestBody LessonStudentMarksRq studentLecture) {
        log.info("fetchStudentMarks: {}", studentLecture);

        if (studentLecture.idLecture() == -1) {
            return ResponseEntity.ok().build();
        }

        List<UserResponse> users = authService.findUserByGroup(studentLecture.groupName());

        List<StudentPerformanceModel> studentsPerformance = users.stream()
                .map(user -> studentLessonService.findByStudentIdAndLessonId(user.getId(), studentLecture.idLecture()))
                .flatMap(Optional::stream)
                .map(lesson -> createStudentPerformance(lesson, users))
                .toList();

        if (studentsPerformance.isEmpty()) {
            studentsPerformance = users.stream()
                    .map(user -> new StudentPerformanceModel(user.getId(), user.getFullName(), 0, false))
                    .toList();
        }

        return ResponseEntity.ok(studentsPerformance);
    }

    private StudentPerformanceModel createStudentPerformance(StudentLesson lesson, List<UserResponse> users) {
        UserResponse user = users.stream()
                .filter(userRes -> userRes.getId().equals(lesson.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String name = user.getFullName();
        return new StudentPerformanceModel(lesson.getStudentId(), name, lesson.getMark(), lesson.getIsPresent());
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
