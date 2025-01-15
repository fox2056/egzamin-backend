package pl.sliepov.egzamin.application.usecase.discipline;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.sliepov.egzamin.domain.model.discipline.Discipline;
import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.domain.port.out.DisciplineRepository;
import pl.sliepov.egzamin.domain.port.out.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManageDisciplinesService {
    private final DisciplineRepository disciplineRepository;
    private final QuestionRepository questionRepository;

    public ManageDisciplinesService(DisciplineRepository disciplineRepository, QuestionRepository questionRepository) {
        this.disciplineRepository = disciplineRepository;
        this.questionRepository = questionRepository;
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

    public Discipline updateDiscipline(Long id, String newName, String newProfessor) {
        Discipline discipline = disciplineRepository.findById(id);

        String updatedName = newName != null ? newName : discipline.getName();
        String updatedProfessor = newProfessor != null ? newProfessor : discipline.getProfessor();

        Discipline updatedDiscipline = new Discipline(
                discipline.getId(),
                updatedName,
                updatedProfessor);

        return disciplineRepository.save(updatedDiscipline);
    }

    @Transactional
    public void mergeDisciplines(Long sourceDisciplineId, Long targetDisciplineId) {
        // Sprawdź czy dyscypliny istnieją
        Optional.ofNullable(disciplineRepository.findById(sourceDisciplineId))
                .orElseThrow(() -> new RuntimeException("Źródłowa dyscyplina nie istnieje"));
        Optional.ofNullable(disciplineRepository.findById(targetDisciplineId))
                .orElseThrow(() -> new RuntimeException("Docelowa dyscyplina nie istnieje"));

        // Przenieś wszystkie pytania do docelowej dyscypliny
        List<Question> questions = questionRepository.findAllByDisciplineId(sourceDisciplineId);
        questions.forEach(question -> {
            Question updatedQuestion = new Question(
                    question.getId(),
                    question.getContent(),
                    question.getType(),
                    question.getCorrectAnswers(),
                    question.getIncorrectAnswers(),
                    targetDisciplineId);
            questionRepository.save(updatedQuestion);
        });

        // Usuń źródłową dyscyplinę
        disciplineRepository.deleteById(sourceDisciplineId);
    }

}
