package pl.sliepov.egzamin.infrastructure.persistence.question;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataQuestionRatingRepository extends JpaRepository<QuestionRatingEntity, Long> {
    List<QuestionRatingEntity> findByQuestion(QuestionEntity question);

    long countByQuestionAndIsPositiveTrue(QuestionEntity question);

    long countByQuestionAndIsPositiveFalse(QuestionEntity question);
}