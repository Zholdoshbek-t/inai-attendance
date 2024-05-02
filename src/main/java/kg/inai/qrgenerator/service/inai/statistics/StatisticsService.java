package kg.inai.qrgenerator.service.inai.statistics;

import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.entity.Attendance;
import kg.inai.qrgenerator.entity.User;
import kg.inai.qrgenerator.entity.repository.AttendanceRepository;
import kg.inai.qrgenerator.entity.repository.GroupRepository;
import kg.inai.qrgenerator.service.inai.statistics.dto.StudentStatisticsDto;
import kg.inai.qrgenerator.service.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final GroupRepository groupRepository;
    private final AttendanceRepository attendanceRepository;

    public List<StudentStatisticsDto> getByFullName(Long groupId, LocalDate from, LocalDate till) {

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        var attendances = getByGroupIdBetweenDates(groupId, from, till);

        return studentsSortedByFullName(group.getStudents(), attendances.size(), groupId);
    }

    public List<StudentStatisticsDto> getByFullNameSubjectId(Long groupId, LocalDate from, LocalDate till, Long subjectId) {

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        var attendances = getByGroupIdBetweenDatesSubjectId(groupId, from, till, subjectId);

        return studentsSortedByFullName(group.getStudents(), attendances.size(), groupId);
    }

    private List<StudentStatisticsDto> studentsSortedByFullName(List<User> students, Integer attendancesSize, Long groupId) {

        return students.stream()
                .map(student -> mapToStudentDto(student, attendancesSize, groupId))
                .sorted(Comparator.comparing(StudentStatisticsDto::getFullName))
                .toList();
    }

    public List<StudentStatisticsDto> getByScoreAsc(Long groupId, LocalDate from, LocalDate till) {

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        var attendances = getByGroupIdBetweenDates(groupId, from, till);

        return studentsSortedByScoreAsc(group.getStudents(), attendances.size(), groupId);
    }

    public List<StudentStatisticsDto> getByScoreAscSubjectId(Long groupId, LocalDate from,
                                               LocalDate till, Long subjectId) {

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        var attendances = getByGroupIdBetweenDatesSubjectId(groupId, from, till, subjectId);

        return studentsSortedByScoreAsc(group.getStudents(), attendances.size(), groupId);
    }

    private List<StudentStatisticsDto> studentsSortedByScoreAsc(List<User> students, Integer attendancesSize, Long groupId) {

        return students.stream()
                .map(student -> mapToStudentDto(student, attendancesSize, groupId))
                .sorted(Comparator.comparing(StudentStatisticsDto::getAbsenceNum))
                .toList();
    }

    public List<StudentStatisticsDto> getByScoreDesc(Long groupId, LocalDate from, LocalDate till) {

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        var attendances = getByGroupIdBetweenDates(groupId, from, till);

        return studentsSortedByScoreDesc(group.getStudents(), attendances.size(), groupId);
    }

    public List<StudentStatisticsDto> getByScoreDescSubjectId(Long groupId, LocalDate from,
                                                LocalDate till, Long subjectId) {

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        var attendances = getByGroupIdBetweenDatesSubjectId(groupId, from, till, subjectId);

        return studentsSortedByScoreDesc(group.getStudents(), attendances.size(), groupId);
    }

    private List<StudentStatisticsDto> studentsSortedByScoreDesc(List<User> students, Integer attendancesSize, Long groupId) {

        return students.stream()
                .map(student -> mapToStudentDto(student, attendancesSize, groupId))
                .sorted(Comparator.comparing(StudentStatisticsDto::getAbsenceNum).reversed())
                .toList();
    }

    private List<Attendance> getByGroupIdBetweenDates(Long groupId, LocalDate from, LocalDate till) {

        return attendanceRepository.findAllByGroupId(groupId).stream()
                .filter(attendance -> {
                    var date = attendance.getDate();
                    return (date.isAfter(from) && date.isBefore(till)) ||
                            (date.isEqual(from) || date.isEqual(till));
                })
                .toList();
    }

    private List<Attendance> getByGroupIdBetweenDatesSubjectId(Long groupId, LocalDate from,
                                                               LocalDate till, Long subjectId) {

        return attendanceRepository.findAllByGroupId(groupId).stream()
                .filter(attendance -> {
                    var date = attendance.getDate();
                    return attendance.getSubjectSchedule().getSubject().getId().equals(subjectId) &&
                            ((date.isAfter(from) && date.isBefore(till)) ||
                                    (date.isEqual(from) || date.isEqual(till)));
                })
                .toList();
    }

    private StudentStatisticsDto mapToStudentDto(User student, Integer attendanceByGroup, Long groupId) {

        var studentAttendanceNum = attendanceRepository.getStudentsWithAttendance(groupId, student.getId());

        return StudentStatisticsDto.builder()
                .fullName(Utils.getFullName(student))
                .absenceNum(attendanceByGroup - studentAttendanceNum)
                .build();
    }
}
