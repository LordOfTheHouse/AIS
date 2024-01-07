package ru.vogu35.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vogu35.backend.models.schedule.ScheduleModel;
import ru.vogu35.backend.services.auth.JwtService;
import ru.vogu35.backend.services.SubjectGroupService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final SubjectGroupService subjectGroup;
    private final JwtService jwtService;

    @Autowired
    public ScheduleController(SubjectGroupService subjectGroup, JwtService jwtService) {
        this.subjectGroup = subjectGroup;
        this.jwtService = jwtService;
    }

    @PreAuthorize("hasAnyRole('client_student', 'client_teacher')")
    @GetMapping
    public ResponseEntity<List<List<ScheduleModel>>> getSchedule(){
        if(jwtService.getGroupIdClaim().equalsIgnoreCase("teacher")){
            log.info("Расписание для учителя");
            return ResponseEntity.ok(subjectGroup.findAllByTeacherId());
        }
        log.info("Расписание для студента");
        return ResponseEntity.ok(subjectGroup.findAllByGroupId());
    }
}
