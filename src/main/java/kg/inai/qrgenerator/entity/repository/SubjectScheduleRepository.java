package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("select ss from tb_subject_schedule ss " +
            "inner join tb_subject s " +
            "on ss.subject.id = s.id " +
            "order by s.year desc , s.semester asc")
    List<SubjectSchedule> getAllBySemesterAndYear(Integer semester, Integer year);
}
