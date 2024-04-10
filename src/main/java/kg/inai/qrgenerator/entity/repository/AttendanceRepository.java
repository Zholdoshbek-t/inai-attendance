package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.Attendance;
import kg.inai.qrgenerator.entity.SubjectSchedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findAllByGroupIdAndDateBetween(Long groupId, LocalDate from, LocalDate till, Sort sort);

    Optional<Attendance> findByDateAndSubjectSchedule(LocalDate date, SubjectSchedule subjectSchedule);

    @Query(value = "SELECT COUNT(*) FROM Attendance a " +
            "INNER JOIN student_attendance sa " +
            "ON sa.student_id = :studentId " +
            "INNER JOIN subject_schedule ss " +
            "ON ss.id = a.subject_schedule_id " +
            "INNER JOIN subject s " +
            "ON s.id = ss.subject_id " +
            "WHERE a.group_id = :groupId " +
            "AND s.semester = :semester " +
            "AND s.year = :year",
            nativeQuery = true)
    Long getStudentsWithAttendance(Long groupId, Long studentId, Integer semester, Integer year);

    Long countAllByGroupId(Long groupId);
}
