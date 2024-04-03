package kg.inai.qrgenerator.service.subject;

import kg.inai.qrgenerator.commons.enums.ClassTime;
import kg.inai.qrgenerator.commons.exception.AlreadyExistsException;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.service.subject.dtos.SubjectScheduleDto;
import kg.inai.qrgenerator.entity.SubjectSchedule;
import kg.inai.qrgenerator.entity.repository.GroupRepository;
import kg.inai.qrgenerator.entity.repository.SubjectRepository;
import kg.inai.qrgenerator.entity.repository.SubjectScheduleRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;
import static kg.inai.qrgenerator.commons.enums.SystemCode.SUCCESS;

@Service
@RequiredArgsConstructor
public class SubjectScheduleService {

    private final SubjectScheduleRepository subjectScheduleRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public RestResponse createSubjectSchedule(SubjectScheduleDto subjectScheduleDto) {
        if(subjectScheduleRepository.existsBySubjectIdAndTeacherIdAndGroupIdAndClassTimeIs(
                subjectScheduleDto.getSubjectId(),
                subjectScheduleDto.getTeacherId(),
                subjectScheduleDto.getGroupId(),
                ClassTime.valueOf(subjectScheduleDto.getClassTime()))) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        var optionalSubject = subjectRepository.findById(subjectScheduleDto.getSubjectId());
        var optionalTeacher = userRepository.findById(subjectScheduleDto.getTeacherId());
        var optionalGroup = groupRepository.findById(subjectScheduleDto.getGroupId());
        var classTime = ClassTime.valueOf(subjectScheduleDto.getClassTime());
        var dayOfWeek = DayOfWeek.valueOf(subjectScheduleDto.getDayOfWeek());

        if(optionalSubject.isEmpty() || optionalTeacher.isEmpty() || optionalGroup.isEmpty()) {
            throw new NotFoundException(VALUE_NOT_FOUND);
        }

        var subjectSchedule = SubjectSchedule.builder()
                .subject(optionalSubject.get())
                .teacher(optionalTeacher.get())
                .group(optionalGroup.get())
                .classTime(classTime)
                .dayOfWeek(dayOfWeek)
                .build();

        subjectScheduleRepository.save(subjectSchedule);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

    public RestResponse updateSubjectSchedule(Long id, SubjectScheduleDto subjectScheduleDto) {
        var subjectSchedule = subjectScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SUBJECT_SCHEDULE_NOT_FOUND));

        var optionalSubject = subjectRepository.findById(subjectScheduleDto.getSubjectId());
        var optionalTeacher = userRepository.findById(subjectScheduleDto.getTeacherId());
        var optionalGroup = groupRepository.findById(subjectScheduleDto.getGroupId());

        if(!subjectScheduleDto.getClassTime().isEmpty()) {
            var classTime = ClassTime.valueOf(subjectScheduleDto.getClassTime());
            subjectSchedule.setClassTime(classTime);
        }

        if(!subjectScheduleDto.getDayOfWeek().isEmpty()) {
            var dayOfWeek = DayOfWeek.valueOf(subjectScheduleDto.getDayOfWeek());
            subjectSchedule.setDayOfWeek(dayOfWeek);
        }

        optionalSubject.ifPresent(subjectSchedule::setSubject);
        optionalGroup.ifPresent(subjectSchedule::setGroup);
        optionalTeacher.ifPresent(subjectSchedule::setTeacher);

        subjectScheduleRepository.save(subjectSchedule);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }
}
