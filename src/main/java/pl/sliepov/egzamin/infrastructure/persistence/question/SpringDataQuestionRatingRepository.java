package pl.sliepov.egzamin.infrastructure.persistence.question;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataQuestionRatingRepository extends JpaRepository<QuestionRatingEntity, Long> {
    List<QuestionRatingEntity> findByQuestionId(Long questionId);

    long countByQuestionIdAndIsPositive(Long questionId, boolean isPositive);

    void deleteAllByQuestionId(Long questionId);
}