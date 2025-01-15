package pl.sliepov.egzamin.application.usecase.question;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.domain.model.question.QuestionType;
import pl.sliepov.egzamin.domain.port.out.QuestionRepository;
import pl.sliepov.egzamin.domain.port.out.QuestionRatingRepository;
import pl.sliepov.egzamin.domain.port.out.DisciplineRepository;
import pl.sliepov.egzamin.infrastructure.web.question.dto.CommentDto;
import pl.sliepov.egzamin.infrastructure.web.question.dto.QuestionRatingStatsDto;
import pl.sliepov.egzamin.infrastructure.web.question.dto.QuestionWithRatingDto;

import java.util.List;

@Service
@Transactional
public class ManageQuestionsService {
    private final QuestionRepository questionRepository;
    private final QuestionRatingRepository questionRatingRepository;
    private final DisciplineRepository disciplineRepository;

    public ManageQuestionsService(
            QuestionRepository questionRepository,
            QuestionRatingRepository questionRatingRepository,
            DisciplineRepository disciplineRepository) {
        this.questionRepository = questionRepository;
        this.questionRatingRepository = questionRatingRepository;
        this.disciplineRepository = disciplineRepository;
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

    @Transactional
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id);
        if (question == null) {
            throw new RuntimeException("Pytanie nie istnieje");
        }

        questionRepository.deleteById(id);

        // Sprawdź czy dyscyplina ma jeszcze pytania
        Long disciplineId = question.getDisciplineId();
        if (questionRepository.findAllByDisciplineId(disciplineId).isEmpty()) {
            disciplineRepository.deleteById(disciplineId);
        }
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

    public QuestionWithRatingDto getQuestionWithRatings(Long id) {
        Question question = questionRepository.findById(id);
        QuestionRatingStatsDto ratings = getQuestionRatings(id);

        return new QuestionWithRatingDto(
                question.getId(),
                question.getContent(),
                question.getType(),
                question.getCorrectAnswers(),
                question.getIncorrectAnswers(),
                question.getDisciplineId(),
                ratings);
    }

    private QuestionRatingStatsDto getQuestionRatings(Long questionId) {
        long positiveCount = questionRatingRepository.countPositiveRatingsByQuestionId(questionId);
        long negativeCount = questionRatingRepository.countNegativeRatingsByQuestionId(questionId);
        List<CommentDto> comments = questionRatingRepository.findByQuestionId(questionId)
                .stream()
                .map(r -> new CommentDto(r.getComment(), r.isPositive()))
                .toList();

        return new QuestionRatingStatsDto(positiveCount, negativeCount, comments);
    }
}