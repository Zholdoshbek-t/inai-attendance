package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.ClassTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTimeRepository extends JpaRepository<ClassTime, Long> {
}
