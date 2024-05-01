package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.ClassDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassDayRepository extends JpaRepository<ClassDay, Long> {

    Optional<ClassDay> findByDayEng(String dayEng);

    Boolean existsByDayEng(String name);

    @Query("SELECT c from tb_class_day c order by c.dayOrder asc")
    List<ClassDay> getClassDays();

    List<ClassDay> findAllByOrderByDayOrder();
}
