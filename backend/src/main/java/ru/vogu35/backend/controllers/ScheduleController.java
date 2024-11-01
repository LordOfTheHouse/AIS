package ru.vogu35.backend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vogu35.backend.models.schedule.ScheduleModel;
import ru.vogu35.backend.auth.JwtService;
import ru.vogu35.backend.services.SubjectGroupService;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

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
    public ResponseEntity<List<List<ScheduleModel>>> getSchedule(@RequestParam(name = "isEvenWeek",required = false) Boolean isEvenWeek){
        if(isEvenWeek == null) {
            // получаем текущую неделю
            LocalDate currentDate = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());
            isEvenWeek = currentWeek % 2 == 0;
        }
        if(jwtService.getGroupIdClaim().equalsIgnoreCase("teacher")){
            log.info("Расписание для учителя");
            return ResponseEntity.ok(subjectGroup.findAllByTeacherId(isEvenWeek));
        }
        log.info("Расписание для студента");
        return ResponseEntity.ok( subjectGroup.findAllByGroupId(isEvenWeek) );
    }
}
