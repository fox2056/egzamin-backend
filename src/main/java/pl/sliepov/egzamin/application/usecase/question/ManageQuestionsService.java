package pl.sliepov.egzamin.application.usecase.question;

import org.springframework.stereotype.Service;
import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.domain.model.question.QuestionType;
import pl.sliepov.egzamin.domain.port.out.QuestionRepository;

import java.util.List;

@Service
public class ManageQuestionsService {
    private final QuestionRepository questionRepository;

    public ManageQuestionsService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(String content, QuestionType type,
            List<String> correctAnswers,
            List<String> incorrectAnswers,
            Long disciplineId) {
        validateAnswers(type, correctAnswers);
        Question question = new Question(null, content, type, correctAnswers, incorrectAnswers, disciplineId);
        return questionRepository.save(question);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> getQuestionsByDiscipline(Long disciplineId) {
        return questionRepository.findAllByDisciplineId(disciplineId);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    private void validateAnswers(QuestionType type, List<String> correctAnswers) {
        if (type == QuestionType.SINGLE_CHOICE && correctAnswers.size() != 1) {
            throw new IllegalArgumentException(
                    "Pytanie jednokrotnego wyboru musi mieć dokładnie jedną poprawną odpowiedź");
        }
        if (type == QuestionType.MULTIPLE_CHOICE && correctAnswers.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pytanie wielokrotnego wyboru musi mieć co najmniej jedną poprawną odpowiedź");
        }
    }

    public Question changeQuestionDiscipline(Long questionId, Long newDisciplineId) {
        Question question = questionRepository.findById(questionId);
        Question updatedQuestion = new Question(
                question.getId(),
                question.getContent(),
                question.getType(),
                question.getCorrectAnswers(),
                question.getIncorrectAnswers(),
                newDisciplineId);
        return questionRepository.save(updatedQuestion);
    }
}