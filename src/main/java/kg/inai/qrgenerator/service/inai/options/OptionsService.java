package kg.inai.qrgenerator.service.inai.options;

import kg.inai.qrgenerator.commons.enums.Role;
import kg.inai.qrgenerator.entity.repository.*;
import kg.inai.qrgenerator.service.inai.options.dto.*;
import kg.inai.qrgenerator.service.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionsService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectScheduleRepository subjectScheduleRepository;
    private final ClassDayRepository classDayRepository;
    private final ClassTimeRepository classTimeRepository;

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

}
