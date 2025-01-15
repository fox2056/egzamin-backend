package pl.sliepov.egzamin.infrastructure.persistence.question;

import org.springframework.stereotype.Repository;
import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.domain.port.out.QuestionRepository;
import pl.sliepov.egzamin.infrastructure.persistence.discipline.DisciplineEntity;
import pl.sliepov.egzamin.infrastructure.persistence.discipline.SpringDataDisciplineRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaQuestionRepository implements QuestionRepository {
    private final SpringDataQuestionRepository questionRepository;
    private final SpringDataDisciplineRepository disciplineRepository;

    public JpaQuestionRepository(SpringDataQuestionRepository questionRepository,
            SpringDataDisciplineRepository disciplineRepository) {
        this.questionRepository = questionRepository;
        this.disciplineRepository = disciplineRepository;
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .map(QuestionEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Override
    public List<Question> findAllByDisciplineId(Long disciplineId) {
        return questionRepository.findAllByDisciplineId(disciplineId)
                .stream()
                .map(QuestionEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Question save(Question question) {
        DisciplineEntity discipline = disciplineRepository.findById(question.getDisciplineId())
                .orElseThrow(() -> new RuntimeException("Discipline not found"));

        QuestionEntity entity = QuestionEntity.fromDomain(question, discipline);
        QuestionEntity saved = questionRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}