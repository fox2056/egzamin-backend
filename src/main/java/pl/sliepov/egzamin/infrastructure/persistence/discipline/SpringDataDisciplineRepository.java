package pl.sliepov.egzamin.infrastructure.persistence.discipline;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataDisciplineRepository extends JpaRepository<DisciplineEntity, Long> {
    Optional<DisciplineEntity> findByName(String name);
}
