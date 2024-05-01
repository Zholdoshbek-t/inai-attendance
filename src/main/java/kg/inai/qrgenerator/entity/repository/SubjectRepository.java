package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Boolean existsByNameAndSemesterAndYear(String name, Integer semester, Integer year);

    List<Subject> findAllByYearAndSemesterOrderByNameAsc(Integer year, Integer semester);
}
