package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Boolean existsByName(String name);

    List<Group> findAllByOrderByName();
}
