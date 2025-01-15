package pl.sliepov.egzamin.infrastructure.persistence.discipline;

import org.springframework.stereotype.Repository;
import pl.sliepov.egzamin.domain.model.discipline.Discipline;
import pl.sliepov.egzamin.domain.port.out.DisciplineRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaDisciplineRepository implements DisciplineRepository {
    private final SpringDataDisciplineRepository springDataRepo;

    public JpaDisciplineRepository(SpringDataDisciplineRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Discipline findById(Long id) {
        return springDataRepo.findById(id)
                .map(DisciplineEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Discipline not found"));
    }

    @Override
    public List<Discipline> findAll() {
        return springDataRepo.findAll()
                .stream()
                .map(DisciplineEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Discipline save(Discipline discipline) {
        // 1) Zamieniamy model domenowy -> encję JPA
        DisciplineEntity entity = DisciplineEntity.fromDomain(discipline);
        // 2) Zapisujemy w bazie
        DisciplineEntity saved = springDataRepo.save(entity);
        // 3) Zamieniamy z powrotem na model domenowy i zwracamy
        return saved.toDomain();
    }

    @Override
    public void deleteById(Long id) {
        springDataRepo.deleteById(id);
    }

    @Override
    public Optional<Discipline> findByName(String name) {
        return springDataRepo.findByName(name)
                .map(DisciplineEntity::toDomain);
    }
}
