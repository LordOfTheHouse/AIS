package ru.vogu35.backend.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Group;
import ru.vogu35.backend.entities.Lesson;
import ru.vogu35.backend.entities.Subject;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.entities.enums.EInstitute;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;
import ru.vogu35.backend.models.auth.UserResponse;
import ru.vogu35.backend.models.schedule.ScheduleModel;
import ru.vogu35.backend.models.schedule.ScheduleTodayModel;
import ru.vogu35.backend.proxies.AuthService;
import ru.vogu35.backend.repositories.SubjectGroupRepository;
import ru.vogu35.backend.services.GroupService;
import ru.vogu35.backend.services.LessonService;
import ru.vogu35.backend.services.SubjectGroupService;
import ru.vogu35.backend.services.SubjectService;
import ru.vogu35.backend.services.auth.JwtService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
public class SubjectGroupServiceImpl implements SubjectGroupService {

    private final SubjectGroupRepository subjectGroupRepository;
    private final JwtService jwtService;
    private final SubjectService subjectService;
    private final GroupService groupService;
    private final LessonService lessonService;
    private final AuthService authService;

    @Autowired
    public SubjectGroupServiceImpl(
            SubjectGroupRepository subjectGroupRepository, JwtService jwtService,
            SubjectService subjectService, GroupService groupService,
            AuthService authService,
            LessonService lessonService
    ) {
        this.subjectGroupRepository = subjectGroupRepository;
        this.jwtService = jwtService;
        this.subjectService = subjectService;
        this.groupService = groupService;
        this.lessonService = lessonService;
        this.authService = authService;
    }

    @Override
    public long save(SubjectGroup subjectGroup) {
        return subjectGroupRepository.save(subjectGroup).getId();
    }

    @Override
    public boolean update(SubjectGroup subjectGroup) {
        if (subjectGroupRepository.existsById(subjectGroup.getId())) {
            subjectGroupRepository.save(subjectGroup);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        if (subjectGroupRepository.existsById(id)) {
            subjectGroupRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<List<ScheduleModel>> findAllByGroupId(boolean isEvenWeek) {

        return IntStream.rangeClosed(1, 6).mapToObj(weekday -> {
                    List<SubjectGroup> subjectGroups = subjectGroupRepository
                            .findAllByGroup_NameAndWeekdayAndWeekEven(jwtService.getGroupIdClaim(), weekday, isEvenWeek);

                    return subjectGroups.stream()
                            .map(subjectGroup -> {
                                Optional<UserResponse> userResponseOptional = authService.findByUserId(
                                        subjectGroup.getTeacherId()
                                );
                                if (userResponseOptional.isPresent()) {
                                    UserResponse teacher = userResponseOptional.get();
                                    String teacherName = teacher.getLastName() + " "
                                            + teacher.getFirstName().charAt(0) + "."
                                            + ((teacher.getMiddleName().isEmpty()) ? "" :
                                            teacher.getMiddleName().charAt(0) + ".");
                                    return new ScheduleModel(subjectGroup, teacherName);
                                }
                                return new ScheduleModel(subjectGroup, "Not Found");
                            })
                            .toList();
                }
        ).toList();
    }

    @Override
    public List<List<ScheduleModel>> findAllByTeacherId(boolean isEvenWeek) {
        return IntStream.rangeClosed(1, 6).mapToObj(weekday -> {
                    // LocalDate currentDate = LocalDate.now();
                    //
                    // // Получаем текущую неделю
                    //WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    //int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());
                    //boolean isEvenWeek = currentWeek % 2 == 0;

                    List<SubjectGroup> subjectGroups = subjectGroupRepository
                            .findAllByTeacherIdAndWeekdayAndWeekEven(jwtService.getSubClaim(), weekday, isEvenWeek);
                    return subjectGroups.stream()
                            .map(subjectGroup -> {
                                        String teacherName = jwtService.getLastNameClaim() + " "
                                                + jwtService.getFirstNameClaim().charAt(0) + "."
                                                + ((jwtService.getMiddleNameClaim().isEmpty()) ? "" :
                                                jwtService.getMiddleNameClaim().charAt(0) + ".");
                                        return new ScheduleModel(subjectGroup, teacherName);
                                    }
                            )
                            .toList();
                }
        ).toList();
    }

    @Override
    public List<GroupModel> findGroupByTeacherId() {
        List<SubjectGroup> subjectGroups = subjectGroupRepository.findAllByTeacherId(jwtService.getSubClaim());
        return subjectGroups.stream()
                .map(subjectGroup -> {
                            Optional<Group> group = groupService.findById(subjectGroup.getGroup().getId());
                            return group.map(subject -> new GroupModel(subject.getId(), subject.getName())).orElseGet(GroupModel::new);
                        }
                )
                .distinct()
                .toList();
    }

    @Override
    public List<GroupModel> findGroupTeacherByInstitute(String institute) {
        return subjectGroupRepository.findGroupTeacherByInstitute(jwtService.getSubClaim(),
                EInstitute.valueOf(institute));
    }

    @Override
    public List<ScheduleTodayModel> findAllTodayByTeacher() {
        LocalDate currentDate = LocalDate.now();

        // Получаем текущую неделю
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());
        boolean isEvenWeek = currentWeek % 2 == 0;
        int currentDay = currentDate.getDayOfWeek().getValue();
        List<SubjectGroup> subjectGroups = subjectGroupRepository
                .findAllByTeacherIdAndWeekdayAndWeekEven(jwtService.getSubClaim(), currentDay, isEvenWeek);
        return subjectGroups.stream().map(subjectGroup -> {
                    List<Lesson> lessons = lessonService.findAllByTodayAndSubjectGroupId(subjectGroup.getId());
                    if (lessons.isEmpty()) {
                        return new ScheduleTodayModel(subjectGroup, "", -1);
                    }
                    return new ScheduleTodayModel(subjectGroup, lessons.get(0).getTopic(), lessons.get(0).getId());
                }
        ).toList();
    }

    @Override
    public List<SubjectModel> findSubjectByTeacherId() {
        List<SubjectGroup> subjectGroups = subjectGroupRepository.findAllByTeacherId(jwtService.getSubClaim());
        return subjectGroups.stream()
                .map(subjectGroup -> {
                            Optional<Subject> subject = subjectService.findById(subjectGroup.getSubject().getId());
                            return subject.map(sub -> new SubjectModel(sub.getId(), sub.getTitle())).orElseGet(SubjectModel::new);
                        }
                )
                .distinct()
                .toList();
    }

    @Override
    public List<SubjectModel> findSubjectTeacherByGroup(String groupName) {
        return subjectGroupRepository.findGroupTeacherByGroup(jwtService.getSubClaim(),groupName);
    }

    @Override
    public List<SubjectGroup> findSubjectTeacherByGroupAndSubject(String groupName, String SubjectName) {
        return subjectGroupRepository.findAllByTeacherIdAndGroup_NameAndAndSubject_Title(jwtService.getSubClaim(),
                                                                                            groupName, SubjectName);
    }

    @Override
    public Optional<SubjectGroup> findStartLecture(String groupName, LocalTime startLecture) {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfWeek().getValue();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());
        boolean isEvenWeek = currentWeek % 2 == 0;
        String idTeacher = jwtService.getSubClaim();
        log.info("curDate: {}, id: {}, groupName: {}, start: {}", currentDay, idTeacher, groupName, startLecture);
        return subjectGroupRepository.findAllByTeacherIdAndGroup_NameAndWeekdayAndWeekEvenAndStart(idTeacher, groupName,
                currentDay, isEvenWeek, startLecture);
    }
}
