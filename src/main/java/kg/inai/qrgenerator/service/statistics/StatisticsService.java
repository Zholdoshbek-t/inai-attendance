package kg.inai.qrgenerator.service.statistics;

import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.entity.User;
import kg.inai.qrgenerator.entity.repository.AttendanceRepository;
import kg.inai.qrgenerator.entity.repository.GroupRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.statistics.dtos.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final GroupRepository groupRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public RestResponse getStudentsList(Long groupId, Integer semester, Integer year) {
        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        var attendanceByGroup = attendanceRepository.countAllByGroupId(group.getId());

        var students = group.getStudents().stream()
                .map(s -> mapToStudentDto(s, attendanceByGroup, groupId, semester, year))
                .toList();

        return RestResponse.builder()
                .message(SUCCESS.getMessage())
                .code(SUCCESS.getCode())
                .body(students)
                .build();
    }

    public RestResponse getStudentsRatingLow(Long groupId, Integer semester, Integer year) {
        return getStudentsRating(groupId, semester, year, true);
    }

    public RestResponse getStudentsRatingHigh(Long groupId, Integer semester, Integer year) {
        return getStudentsRating(groupId, semester, year, false);
    }

    private RestResponse getStudentsRating(Long groupId, Integer semester, Integer year, boolean ratingLow) {
        var attendanceByGroup = attendanceRepository.countAllByGroupId(groupId);

        List<StudentDto> students;

        if (ratingLow) {
            students = userRepository.findAllByGroupId(groupId).stream()
                    .sorted((s1, s2) -> Long.compare(
                            attendanceRepository.getStudentsWithAttendance(groupId, s2.getId(), semester, year),
                            attendanceRepository.getStudentsWithAttendance(groupId, s1.getId(), semester, year)))
                    .map(s -> mapToStudentDto(s, attendanceByGroup, groupId, semester, year))
                    .toList();
        } else {
            students = userRepository.findAllByGroupId(groupId).stream()
                    .sorted((s1, s2) -> Long.compare(
                            attendanceRepository.getStudentsWithAttendance(groupId, s1.getId(), semester, year),
                            attendanceRepository.getStudentsWithAttendance(groupId, s2.getId(), semester, year)))
                    .map(s -> mapToStudentDto(s, attendanceByGroup, groupId, semester, year))
                    .toList();
        }

        return RestResponse.builder()
                .message(SUCCESS.getMessage())
                .code(SUCCESS.getCode())
                .body(students)
                .build();
    }

    private StudentDto mapToStudentDto(User student, Long attendanceByGroup, Long groupId,
                                       Integer semester, Integer year) {
        var studentAttendanceNum =
                attendanceRepository.getStudentsWithAttendance(groupId, student.getId(), semester, year);

        return StudentDto.builder()
                .absenceNum(attendanceByGroup - studentAttendanceNum)
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .middleName(!student.getMiddleName().isEmpty() ? student.getMiddleName() : "")
                .build();
    }
}
