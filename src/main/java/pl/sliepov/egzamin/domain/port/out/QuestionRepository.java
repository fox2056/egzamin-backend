package pl.sliepov.egzamin.domain.port.out;

import pl.sliepov.egzamin.domain.model.question.Question;
import java.util.List;

public interface QuestionRepository {
    Question findById(Long id);

    List<Question> findAllByDisciplineId(Long disciplineId);

    Question save(Question question);

    void deleteById(Long id);
}