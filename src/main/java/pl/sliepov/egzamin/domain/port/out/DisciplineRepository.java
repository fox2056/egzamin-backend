package pl.sliepov.egzamin.domain.port.out;

import pl.sliepov.egzamin.domain.model.discipline.Discipline;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository {

    Discipline findById(Long id);

    List<Discipline> findAll();

    Discipline save(Discipline discipline);

    void deleteById(Long id);

    Optional<Discipline> findByName(String name);
}
