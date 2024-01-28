package ru.vogu35.backend.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vogu35.backend.entities.enums.EInstitute;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;
import ru.vogu35.backend.proxies.KeycloakApiProxy;
import ru.vogu35.backend.services.SubjectGroupService;
import ru.vogu35.backend.services.auth.JwtService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/perfomance")
public class StudentPerformanceController {

    private final SubjectGroupService subjectGroupService;

    @Autowired
    public StudentPerformanceController(SubjectGroupService subjectGroupService) {
        this.subjectGroupService = subjectGroupService;
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
        if(groups.isEmpty()){
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
        if(subjects.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subjects);
    }
}
