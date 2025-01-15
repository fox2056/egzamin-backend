package pl.sliepov.egzamin.infrastructure.persistence.question;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataQuestionRepository extends JpaRepository<QuestionEntity, Long> {
    List<QuestionEntity> findAllByDisciplineId(Long disciplineId);
}