package kg.inai.qrgenerator.entity.repository;

import kg.inai.qrgenerator.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Boolean existsByName(String name);
}
