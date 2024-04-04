package kg.inai.qrgenerator.service.attendance;

import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.entity.Attendance;
import kg.inai.qrgenerator.entity.User;
import kg.inai.qrgenerator.entity.repository.AttendanceRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.attendance.dtos.AttendanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public RestResponse attendTheClass(Long attendanceId, Long studentId) {
        var attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException(VALUE_NOT_FOUND));

        var student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        attendance.getStudents().add(student);
        attendanceRepository.save(attendance);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

    public List<String> getClassAttendance(Long attendanceId) {
        var attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException(VALUE_NOT_FOUND));

        return attendance.getStudents().stream()
                .map(this::extractFullName)
                .sorted()
                .toList();
    }

    public RestResponse getAttendances(Long groupId, LocalDate from, LocalDate till) {

        var attendances = attendanceRepository.findAllByGroupIdAndDateBetween(groupId, from, till,
                Sort.by("date").descending().and(
                Sort.by("classDay").ascending().and(
                Sort.by("classTime").ascending()))).stream()
                    .map(this::mapToAttendanceDto)
                    .toList();

        return RestResponse.builder()
                .message(SUCCESS.getMessage())
                .code(SUCCESS.getCode())
                .body(attendances)
                .build();
    }

    private String extractFullName(User user) {
        if (user.getMiddleName().isEmpty()) {
            return user.getLastName() + " " + user.getFirstName();
        }

        return user.getLastName() + " " + user.getFirstName() + " " + user.getMiddleName();
    }

    private AttendanceDto mapToAttendanceDto(Attendance attendance) {
        return AttendanceDto.builder()
                .teacher(extractFullName(attendance.getTeacher()))
                .group(attendance.getGroup().getName())
                .date(dateTimeFormatter.format(attendance.getDate()))
                .subject(attendance.getSubjectSchedule().getSubject().getName())
                .studentsCount(attendance.getStudents().size())
                .classTime(attendance.getSubjectSchedule().getClassTime().getHours() + ":" +
                        attendance.getSubjectSchedule().getClassTime().getMinutes())
                .dayOfWeek(attendance.getSubjectSchedule().getDayOfWeek().getDayOfWeek())
                .build();
    }
}
