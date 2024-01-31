package ru.vogu35.backend.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.entities.StudentLesson;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.models.StartLecture;
import ru.vogu35.backend.models.UserResponse;
import ru.vogu35.backend.models.schedule.ScheduleTodayModel;
import ru.vogu35.backend.proxies.KeycloakApiProxy;
import ru.vogu35.backend.services.LessonService;
import ru.vogu35.backend.services.StudentLessonService;
import ru.vogu35.backend.services.SubjectGroupService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/lecture")
public class LectureController {

    private final KeycloakApiProxy keycloakApiProxy;
    private final SubjectGroupService subjectGroupService;
    private final LessonService lessonService;
    private final StudentLessonService studentLessonService;


    public LectureController(KeycloakApiProxy keycloakApiProxy, SubjectGroupService subjectGroupService,
                             LessonService lessonService, StudentLessonService studentLessonService) {
        this.keycloakApiProxy = keycloakApiProxy;
        this.subjectGroupService = subjectGroupService;
        this.lessonService = lessonService;
        this.studentLessonService = studentLessonService;
    }

    @PreAuthorize("hasRole('client_teacher')")
    @GetMapping("/groups/today")
    public ResponseEntity<List<ScheduleTodayModel>> getGroups(){
        return ResponseEntity.ok(subjectGroupService.findAllTodayByTeacher());
    }
    @PreAuthorize("hasRole('client_teacher')")
    @GetMapping("/{group}")
    public ResponseEntity<List<UserResponse>> getStudentGroup(@PathVariable("group") String groupName){
        log.info("students group: {}", groupName);
        return ResponseEntity.ok(keycloakApiProxy.findUserByGroup(groupName));
    }

    @PreAuthorize("hasRole('client_teacher')")
    @PostMapping("/{groupName}")
    public ResponseEntity<?> startLecture(@PathVariable String groupName, @RequestBody StartLecture startLecture){
        log.info("Create LESSON: {}", startLecture);
        Optional<SubjectGroup> subjectGroups = subjectGroupService.findStartLecture(groupName, startLecture.getStartLecture());
        if(subjectGroups.isEmpty()){
            return ResponseEntity.badRequest().body("Предмет или  группа не определена");
        }
        if(lessonService.createLecture(subjectGroups.get(), groupName, startLecture.getTopic())){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('client_teacher')")
    @PutMapping("/{idLesson}/groups/{idStudent}/present")
    public ResponseEntity<?> updatePresentStudent(@PathVariable @NotBlank String idStudent,
                                                  @PathVariable @NotBlank long idLesson){
        log.info("update present");
        Optional<StudentLesson> studentLesson = studentLessonService.findByStudentIdAndLessonId(idStudent, idLesson);
        if(studentLesson.isPresent()) {
            if(studentLesson.get().getIsPresent() != null) {
                studentLesson.get().setIsPresent(!studentLesson.get().getIsPresent());
            }
            studentLessonService.save(studentLesson.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('client_teacher')")
    @PutMapping("{idLesson}/groups/{idStudent}")
    public ResponseEntity<?> updateMarkStudent(@PathVariable @NotBlank String idStudent,
                                               @PathVariable @NotBlank long idLesson,
                                               @RequestParam int mark){
        log.info("update mark");
        Optional<StudentLesson> studentLesson = studentLessonService.findByStudentIdAndLessonId(idStudent, idLesson);
        if(studentLesson.isPresent()) {
            if(studentLesson.get().getIsPresent() == null || !studentLesson.get().getIsPresent()) {
                return ResponseEntity.badRequest().body("Студент отсутствует");
            }

            if(mark >= 0 && mark <= 100){
                studentLesson.get().setMark(mark);
                studentLessonService.save(studentLesson.get());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
