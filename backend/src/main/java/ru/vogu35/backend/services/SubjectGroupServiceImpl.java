package ru.vogu35.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.Group;
import ru.vogu35.backend.entities.Subject;
import ru.vogu35.backend.entities.SubjectGroup;
import ru.vogu35.backend.entities.Teacher;
import ru.vogu35.backend.models.GroupModel;
import ru.vogu35.backend.models.SubjectModel;
import ru.vogu35.backend.models.schedule.ScheduleModel;
import ru.vogu35.backend.repositories.SubjectGroupRepository;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class SubjectGroupServiceImpl implements SubjectGroupService {

    private final SubjectGroupRepository subjectGroupRepository;
    private final JwtService jwtService;
    private final SubjectService subjectService;
    private final GroupService groupService;
    private final TeacherService teacherService;

    @Autowired
    public SubjectGroupServiceImpl(
            SubjectGroupRepository subjectGroupRepository, JwtService jwtService,
            SubjectService subjectService, GroupService groupService,
            TeacherService teacherService
    ) {
        this.subjectGroupRepository = subjectGroupRepository;
        this.jwtService = jwtService;
        this.subjectService = subjectService;
        this.groupService = groupService;
        this.teacherService = teacherService;
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

    @Override
    public List<List<ScheduleModel>> findAllByGroupId() {

        return IntStream.rangeClosed(1, 6).mapToObj(weekday -> {
                    LocalDate currentDate = LocalDate.now();

                    // Получаем текущую неделю
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());
                    boolean isEvenWeek = currentWeek % 2 == 0;

                    List<SubjectGroup> subjectGroups = subjectGroupRepository
                            .findAllByGroup_NameAndWeekdayAndWeekEven(jwtService.getGroupIdClaim(), weekday, isEvenWeek);

                    return subjectGroups.stream()
                            .map(subjectGroup -> {
                                Optional<Teacher> teacherOptional = teacherService.findById(subjectGroup.getTeacherId());
                                if (teacherOptional.isPresent()) {
                                    Teacher teacher = teacherOptional.get();
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
    public List<List<ScheduleModel>> findAllByTeacherId() {
        return IntStream.rangeClosed(1, 6).mapToObj(weekday -> {
                    LocalDate currentDate = LocalDate.now();

                    // Получаем текущую неделю
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    int currentWeek = currentDate.get(weekFields.weekOfWeekBasedYear());
                    boolean isEvenWeek = currentWeek % 2 == 0;

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
}
