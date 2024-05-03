package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectScheduleRepository extends JpaRepository<SubjectSchedule, Long> {

    Boolean existsBySubjectIdAndGroupIdAndTeacherIdAndClassDayIdAndClassTimeId(
            Long subjectId, Long groupId, Long teacherId, Long classDayId, Long classTimeId
    );

    Boolean existsByTeacherIdAndClassDayIdAndClassTimeId(Long teacherId, Long classDayId, Long classTimeId);

    List<SubjectSchedule> findAllByTeacherIdAndClassDayId(Long teacherId, Long classDayId);

    List<SubjectSchedule> findAllByClassDay_DayEngAndGroupId(String dayEng, Long groupId);

    List<SubjectSchedule> findAllByOrderBySubjectSemesterAscSubjectYearDesc(Integer semester, Integer year);
}
