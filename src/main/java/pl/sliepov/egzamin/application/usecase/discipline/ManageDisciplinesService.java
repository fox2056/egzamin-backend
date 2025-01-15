package pl.sliepov.egzamin.application.usecase.discipline;

import pl.sliepov.egzamin.domain.model.discipline.Discipline;
import pl.sliepov.egzamin.domain.port.out.DisciplineRepository;

import java.util.List;
import java.util.Optional;

public class ManageDisciplinesService {
    private final DisciplineRepository disciplineRepository;

    public ManageDisciplinesService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public Discipline createDiscipline(String name, String professor) {
        Discipline discipline = new Discipline(null, name, professor);
        return disciplineRepository.save(discipline);
    }

    public List<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    public Discipline getDiscipline(Long id) {
        return disciplineRepository.findById(id);
    }

    public void deleteDiscipline(Long id) {
        disciplineRepository.deleteById(id);
    }

    public Optional<Discipline> findByName(String name) {
        return disciplineRepository.findByName(name);
    }

}
