package kg.inai.qrgenerator.service.inai.subject;

import kg.inai.qrgenerator.commons.exception.AlreadyExistsException;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.entity.ClassDay;
import kg.inai.qrgenerator.entity.Group;
import kg.inai.qrgenerator.entity.SubjectSchedule;
import kg.inai.qrgenerator.entity.repository.*;
import kg.inai.qrgenerator.service.inai.subject.dto.ClassDto;
import kg.inai.qrgenerator.service.inai.subject.dto.StudentClassDto;
import kg.inai.qrgenerator.service.inai.subject.dto.SubjectDto;
import kg.inai.qrgenerator.entity.Subject;
import kg.inai.qrgenerator.service.inai.subject.dto.SubjectScheduleDto;
import kg.inai.qrgenerator.service.utils.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectScheduleRepository subjectScheduleRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ClassDayRepository classDayRepository;
    private final ClassTimeRepository classTimeRepository;

    public RestResponse createSubject(SubjectDto subjectDto) {

        if (subjectRepository.existsByNameAndSemesterAndYear(subjectDto.getName(), subjectDto.getSemester(),
                subjectDto.getYear())) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        var subject = Subject.builder()
                .name(subjectDto.getName())
                .semester(subjectDto.getSemester())
                .year(subjectDto.getYear())
                .build();

        subjectRepository.save(subject);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse updateSubject(Long id, SubjectDto subjectDto) {

        if (subjectRepository.existsByNameAndSemesterAndYear(subjectDto.getName(), subjectDto.getSemester(),
                subjectDto.getYear())) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        var subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SUBJECT_NOT_FOUND));

        subject.setName(subjectDto.getName());
        subject.setYear(subjectDto.getYear());
        subject.setSemester(subjectDto.getSemester());

        subjectRepository.save(subject);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse createSubjectSchedule(SubjectScheduleDto subjectScheduleDto) {

        if (subjectScheduleRepository.existsByTeacherIdAndClassDayIdAndClassTimeId(
                subjectScheduleDto.getTeacherId(),
                subjectScheduleDto.getClassDayId(),
                subjectScheduleDto.getClassTimeId())) {
            throw new AlreadyExistsException(TEACHER_HAS_SUBJECT);
        } else if (subjectScheduleRepository.existsBySubjectIdAndGroupIdAndTeacherIdAndClassDayIdAndClassTimeId(
                subjectScheduleDto.getSubjectId(),
                subjectScheduleDto.getGroupId(),
                subjectScheduleDto.getTeacherId(),
                subjectScheduleDto.getClassDayId(),
                subjectScheduleDto.getClassTimeId())) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        var optionalSubject = subjectRepository.findById(subjectScheduleDto.getSubjectId());
        var optionalTeacher = userRepository.findById(subjectScheduleDto.getTeacherId());
        var optionalGroup = groupRepository.findById(subjectScheduleDto.getGroupId());
        var optionalClassTime = classTimeRepository.findById(subjectScheduleDto.getClassTimeId());
        var optionalClassDay = classDayRepository.findById(subjectScheduleDto.getClassDayId());

        if (optionalSubject.isEmpty() || optionalTeacher.isEmpty() || optionalGroup.isEmpty()
                || optionalClassTime.isEmpty() || optionalClassDay.isEmpty()) {
            throw new NotFoundException(VALUE_NOT_FOUND);
        }

        var subjectSchedule = SubjectSchedule.builder()
                .subject(optionalSubject.get())
                .teacher(optionalTeacher.get())
                .group(optionalGroup.get())
                .classTime(optionalClassTime.get())
                .classDay(optionalClassDay.get())
                .build();

        subjectScheduleRepository.save(subjectSchedule);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse updateSubjectSchedule(Long id, SubjectScheduleDto subjectScheduleDto) {

        var subjectSchedule = subjectScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SUBJECT_SCHEDULE_NOT_FOUND));

        if (subjectScheduleRepository.existsByTeacherIdAndClassDayIdAndClassTimeId(
                subjectScheduleDto.getTeacherId(),
                subjectScheduleDto.getClassDayId(),
                subjectScheduleDto.getClassTimeId())) {
            throw new AlreadyExistsException(TEACHER_HAS_SUBJECT);
        } else if (subjectScheduleRepository.existsBySubjectIdAndGroupIdAndTeacherIdAndClassDayIdAndClassTimeId(
                subjectScheduleDto.getSubjectId(),
                subjectScheduleDto.getGroupId(),
                subjectScheduleDto.getTeacherId(),
                subjectScheduleDto.getClassDayId(),
                subjectScheduleDto.getClassTimeId())) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        var optionalSubject = subjectRepository.findById(subjectScheduleDto.getSubjectId());
        var optionalTeacher = userRepository.findById(subjectScheduleDto.getTeacherId());
        var optionalGroup = groupRepository.findById(subjectScheduleDto.getGroupId());
        var optionalClassTime = classTimeRepository.findById(subjectScheduleDto.getClassTimeId());
        var optionalClassDay = classDayRepository.findById(subjectScheduleDto.getClassDayId());

        optionalSubject.ifPresent(subjectSchedule::setSubject);
        optionalGroup.ifPresent(subjectSchedule::setGroup);
        optionalTeacher.ifPresent(subjectSchedule::setTeacher);
        optionalClassTime.ifPresent(subjectSchedule::setClassTime);
        optionalClassDay.ifPresent(subjectSchedule::setClassDay);

        subjectScheduleRepository.save(subjectSchedule);

        return ResponseMapper.responseSuccess();
    }

    public List<String> getAllByYearAndSemester(Integer year, Integer semester) {

        return subjectRepository.findAllByYearAndSemesterOrderByNameAsc(year, semester).stream()
                .map(Subject::getName)
                .toList();
    }

    public List<ClassDto> getTeachersClassesToday(Long teacherId) {

        var teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        var today = LocalDate.now().getDayOfWeek().name();
        var classDay = classDayRepository.findByDayEng(today)
                .orElseThrow(() -> new NotFoundException(VALUE_NOT_FOUND));

        return mapToClassDtoList(teacher.getId(), classDay.getId());
    }

    public Map<String, List<ClassDto>> getTeachersWeekClasses(Long teacherId) {

        var teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Map<String, List<ClassDto>> dayClassesDto = new LinkedHashMap<>();

        var classDays = classDayRepository.getClassDays();

        for (ClassDay classDay : classDays) {
            var classes = mapToClassDtoList(teacher.getId(), classDay.getId());
            dayClassesDto.put(classDay.getDayRus(), classes);
        }

        return dayClassesDto;
    }

    public List<StudentClassDto> getStudentClasses(Long groupId) {

        if(!groupRepository.existsById(groupId)) {
            throw new NotFoundException(GROUP_NOT_FOUND);
        }

        var currentDay = LocalDate.now().getDayOfWeek().name();

        return subjectScheduleRepository.findAllByClassDay_DayEngAndGroupId(currentDay, groupId)
                .stream()
                .sorted(Comparator.comparing(schedule -> schedule.getClassTime().getClassHours()))
                .sorted(Comparator.comparing(schedule -> schedule.getClassTime().getClassMinutes()))
                .map(schedule -> {
                    var hours = schedule.getClassTime().getClassHours() < 10 ?
                            "0" + schedule.getClassTime().getClassHours() :
                            String.valueOf(schedule.getClassTime().getClassHours());

                    var minutes = schedule.getClassTime().getClassMinutes() < 10 ?
                            "0" + schedule.getClassTime().getClassMinutes() :
                            String.valueOf(schedule.getClassTime().getClassMinutes());

                    return StudentClassDto.builder()
                            .subjectScheduleId(schedule.getId())
                            .subjectName(schedule.getSubject().getName())
                            .classTime(hours + ":" + minutes)
                            .build();
                })
                .toList();
    }

    private List<ClassDto> mapToClassDtoList(Long teacherId, Long classDayId) {

        return subjectScheduleRepository.findAllByTeacherIdAndClassDayId(teacherId, classDayId)
                .stream()
                .sorted(Comparator.comparing(schedule -> schedule.getClassTime().getClassHours()))
                .sorted(Comparator.comparing(schedule -> schedule.getClassTime().getClassMinutes()))
                .map(schedule -> {
                    var hours = schedule.getClassTime().getClassHours() < 10 ?
                            "0" + schedule.getClassTime().getClassHours() :
                            String.valueOf(schedule.getClassTime().getClassHours());

                    var minutes = schedule.getClassTime().getClassMinutes() < 10 ?
                            "0" + schedule.getClassTime().getClassMinutes() :
                            String.valueOf(schedule.getClassTime().getClassMinutes());

                    return ClassDto.builder()
                            .subjectScheduleId(schedule.getId())
                            .subjectName(schedule.getSubject().getName())
                            .groupName(schedule.getGroup().getName())
                            .classTime(hours + ":" + minutes)
                            .build();
                })
                .toList();
    }
}
