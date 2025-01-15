package pl.sliepov.egzamin.domain.port.out;

import pl.sliepov.egzamin.domain.model.question.QuestionRating;
import java.util.List;

public interface QuestionRatingRepository {
    QuestionRating save(QuestionRating rating);

    List<QuestionRating> findByQuestionId(Long questionId);

    long countPositiveRatingsByQuestionId(Long questionId);

    long countNegativeRatingsByQuestionId(Long questionId);

    void deleteAllByQuestionId(Long questionId);
}