package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.commons.enums.ClassDay;
import kg.inai.qrgenerator.commons.enums.ClassTime;
import kg.inai.qrgenerator.entity.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Optional;

public interface SubjectScheduleRepository extends JpaRepository<SubjectSchedule, Long> {

    Optional<SubjectSchedule> findByTeacherIdAndClassTimeIsAndDayOfWeekIs(Long id, ClassTime classTime,
                                                                          ClassDay dayOfWeek);

    Boolean existsBySubjectIdAndTeacherIdAndGroupIdAndClassTimeIs(
            Long subjectId, Long teacherId, Long groupId, ClassTime classTime);
}
