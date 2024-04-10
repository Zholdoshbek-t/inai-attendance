package kg.inai.qrgenerator.service.qr;

import kg.inai.qrgenerator.commons.enums.ClassDay;
import kg.inai.qrgenerator.commons.enums.ClassTime;
import kg.inai.qrgenerator.commons.exception.AlreadyExistsException;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.entity.SubjectSchedule;
import kg.inai.qrgenerator.entity.repository.GroupRepository;
import kg.inai.qrgenerator.entity.repository.SubjectScheduleRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.qr.dto.SubjectDto;
import kg.inai.qrgenerator.entity.Subject;
import kg.inai.qrgenerator.entity.repository.SubjectRepository;
import kg.inai.qrgenerator.service.qr.dto.SubjectScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectScheduleRepository subjectScheduleRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

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

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

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
        var dayOfWeek = ClassDay.valueOf(subjectScheduleDto.getDayOfWeek());

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
            var dayOfWeek = ClassDay.valueOf(subjectScheduleDto.getDayOfWeek());
            subjectSchedule.setDayOfWeek(dayOfWeek);
        }

        optionalSubject.ifPresent(subjectSchedule::setSubject);
        optionalGroup.ifPresent(subjectSchedule::setGroup);
        optionalTeacher.ifPresent(subjectSchedule::setTeacher);

        subjectScheduleRepository.save(subjectSchedule);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }
}
