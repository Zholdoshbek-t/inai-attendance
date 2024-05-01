package kg.inai.qrgenerator.service.inai.attendance;

import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.entity.repository.AttendanceRepository;
import kg.inai.qrgenerator.entity.repository.SubjectScheduleRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.utils.ResponseMapper;
import kg.inai.qrgenerator.service.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final SubjectScheduleRepository subjectScheduleRepository;
    private final UserRepository userRepository;

    public RestResponse attendTheClass(Long attendanceId, Long studentId) {

        var attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException(VALUE_NOT_FOUND));

        var student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        var difference = Duration.between(attendance.getTime(), LocalTime.now());

        if(!LocalDate.now().isEqual(attendance.getDate()) ||
                difference.compareTo(Duration.ofMinutes(10)) > 0) {
            return RestResponse.builder()
                    .message(LECTURE_TIME_EXPIRE.getMessage())
                    .code(LECTURE_TIME_EXPIRE.getCode())
                    .build();
        }

        attendance.getStudents().add(student);
        attendanceRepository.save(attendance);

        return ResponseMapper.responseSuccess();
    }

    public List<String> getCurrentClassAttendanceBySubjectSchedule(Long subjectScheduleId) {

        var subjectSchedule = subjectScheduleRepository.findById(subjectScheduleId)
                        .orElseThrow(() -> new NotFoundException(SUBJECT_NOT_FOUND));

        var attendance = attendanceRepository.findByDateAndSubjectSchedule(LocalDate.now(), subjectSchedule)
                .orElseThrow(() -> new NotFoundException(VALUE_NOT_FOUND));

        return attendance.getStudents().stream()
                .map(Utils::getFullName)
                .sorted()
                .toList();
    }
}
