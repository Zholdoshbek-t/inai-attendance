package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.Attendance;
import kg.inai.qrgenerator.entity.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByDateAndSubjectSchedule(LocalDate date, SubjectSchedule subjectSchedule);

    @Query(value = "SELECT COUNT(*) FROM tb_attendance a " +
            "INNER JOIN tb_student_attendance sa " +
            "ON sa.student_id = :studentId " +
            "INNER JOIN tb_subject_schedule ss " +
            "ON ss.id = a.subject_schedule_id " +
            "INNER JOIN tb_subject s " +
            "ON s.id = ss.subject_id " +
            "WHERE a.group_id = :groupId",
            nativeQuery = true)
    Long getStudentsWithAttendance(Long groupId, Long studentId);

    List<Attendance> findAllByGroupId(Long groupId);
}
