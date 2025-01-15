package pl.sliepov.egzamin.infrastructure.persistence.question;

import org.springframework.stereotype.Repository;
import pl.sliepov.egzamin.domain.model.question.QuestionRating;
import pl.sliepov.egzamin.domain.port.out.QuestionRatingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaQuestionRatingRepository implements QuestionRatingRepository {
    private final SpringDataQuestionRatingRepository ratingRepository;
    private final SpringDataQuestionRepository questionRepository;

    public JpaQuestionRatingRepository(SpringDataQuestionRatingRepository ratingRepository,
            SpringDataQuestionRepository questionRepository) {
        this.ratingRepository = ratingRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionRating save(QuestionRating rating) {
        QuestionEntity question = questionRepository.findById(rating.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        QuestionRatingEntity entity = QuestionRatingEntity.fromDomain(rating, question);
        QuestionRatingEntity saved = ratingRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public List<QuestionRating> findByQuestionId(Long questionId) {
        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        return ratingRepository.findByQuestion(question).stream()
                .map(QuestionRatingEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countPositiveRatingsByQuestionId(Long questionId) {
        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return ratingRepository.countByQuestionAndIsPositiveTrue(question);
    }

    @Override
    public long countNegativeRatingsByQuestionId(Long questionId) {
        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return ratingRepository.countByQuestionAndIsPositiveFalse(question);
    }
}