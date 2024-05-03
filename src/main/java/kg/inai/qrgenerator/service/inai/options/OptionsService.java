package kg.inai.qrgenerator.service.inai.options;

import kg.inai.qrgenerator.commons.enums.Role;
import kg.inai.qrgenerator.entity.repository.*;
import kg.inai.qrgenerator.service.inai.options.dto.*;
import kg.inai.qrgenerator.service.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionsService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final ClassDayRepository classDayRepository;
    private final ClassTimeRepository classTimeRepository;
    private final SubjectScheduleRepository subjectScheduleRepository;

    public List<TeacherOptionDto> getTeachers() {

        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(Role.TEACHER))
                .map(user -> TeacherOptionDto.builder().id(user.getId()).name(Utils.getFullName(user)).build())
                .toList();
    }

    public List<RoleOptionDto> getRoles() {

        return Arrays.stream(Role.values())
                .map(role -> RoleOptionDto.builder().roleName(role.name()).build())
                .toList();
    }

    public List<SubjectOptionDto> getSubjects() {

        return subjectRepository.findAll().stream()
                .map(subject -> SubjectOptionDto.builder()
                        .id(subject.getId())
                        .name(subject.getName())
                        .semester(subject.getSemester())
                        .year(subject.getYear())
                        .build())
                .toList();
    }

    public List<ClassTimeOptionDto> getClassTimes() {

        return classTimeRepository.findAllByOrderByClassHoursAscClassMinutesAsc().stream()
                .map(classTime -> {
                    var hours = classTime.getClassHours() < 10 ?
                            "0" + classTime.getClassHours() :
                            String.valueOf(classTime.getClassHours());

                    var minutes = classTime.getClassMinutes() < 10 ?
                            "0" + classTime.getClassMinutes() :
                            String.valueOf(classTime.getClassMinutes());

                    return ClassTimeOptionDto.builder()
                            .id(classTime.getId())
                            .time(hours + ":" + minutes)
                            .build();
                })
                .toList();

    }

    public List<ClassDayOptionDto> getClassDays() {

        return classDayRepository.findAllByOrderByDayOrder().stream()
                .map(classDay -> ClassDayOptionDto.builder()
                        .id(classDay.getId())
                        .name(classDay.getDayRus())
                        .build())
                .toList();
    }

    public List<GroupOptionDto> getGroups() {

        return groupRepository.findAllByOrderByName().stream()
                .map(group -> GroupOptionDto.builder()
                        .id(group.getId())
                        .name(group.getName())
                        .build())
                .toList();
    }

    public List<StudentOptionDto> getStudents() {

        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(Role.STUDENT))
                .map(user -> StudentOptionDto.builder()
                        .id(user.getId())
                        .name(Utils.getFullName(user))
                        .build())
                .sorted(Comparator.comparing(StudentOptionDto::getName))
                .toList();
    }

    public List<ScheduleOptionDto> getSubjectSchedules() {

        var year = LocalDate.now().getYear();
        var month = LocalDate.now().getMonthValue();
        var semester = month > 8 ? 1 : 2;

        return subjectScheduleRepository.getAllBySemesterAndYear(semester, year)
                .stream()
                .map(schedule -> {

                    var classTime = schedule.getClassTime();

                    var hours = classTime.getClassHours() < 10 ?
                            "0" + classTime.getClassHours() :
                            String.valueOf(classTime.getClassHours());

                    var minutes = classTime.getClassMinutes() < 10 ?
                            "0" + classTime.getClassMinutes() :
                            String.valueOf(classTime.getClassMinutes());

                    return ScheduleOptionDto.builder()
                            .subjectScheduleId(schedule.getId())
                            .teacher(Utils.getFullName(schedule.getTeacher()))
                            .subjectName(schedule.getSubject().getName())
                            .classTime(hours + ":" + minutes)
                            .classDay(schedule.getClassDay().getDayRus())
                            .build();
                })
                .toList();
    }

    public List<UserOptionDto> getUsers() {

        return userRepository.findAllByOrderByIdAsc().stream()
                .map(user -> UserOptionDto.builder()
                        .id(user.getId())
                        .name(Utils.getFullName(user))
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .role(user.getRole().getNameLocal())
                        .group(user.getRole().equals(Role.STUDENT) ?
                                user.getGroup().getName() :
                                "НЕ СТУДЕНТ")
                        .build())
                .toList();
    }
}
