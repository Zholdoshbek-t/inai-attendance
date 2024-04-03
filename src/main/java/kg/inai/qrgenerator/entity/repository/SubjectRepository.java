package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Boolean existsByNameAndSemesterAndYear(String name, Integer semester, Integer year);
}
