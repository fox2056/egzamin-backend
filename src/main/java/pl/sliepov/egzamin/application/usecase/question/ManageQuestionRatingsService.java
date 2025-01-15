package pl.sliepov.egzamin.application.usecase.question;

import org.springframework.stereotype.Service;
import pl.sliepov.egzamin.domain.model.question.QuestionRating;
import pl.sliepov.egzamin.domain.port.out.QuestionRatingRepository;

@Service
public class ManageQuestionRatingsService {
    private final QuestionRatingRepository ratingRepository;

    public ManageQuestionRatingsService(QuestionRatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public QuestionRating rateQuestion(Long questionId, boolean isPositive, String comment) {
        QuestionRating rating = new QuestionRating(null, questionId, isPositive, comment);
        return ratingRepository.save(rating);
    }

    public RatingStats getQuestionRatingStats(Long questionId) {
        long positiveCount = ratingRepository.countPositiveRatingsByQuestionId(questionId);
        long negativeCount = ratingRepository.countNegativeRatingsByQuestionId(questionId);
        return new RatingStats(positiveCount, negativeCount);
    }

    public record RatingStats(long positiveCount, long negativeCount) {
    }
}