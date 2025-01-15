package pl.sliepov.egzamin.infrastructure.persistence.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sliepov.egzamin.domain.model.question.QuestionRating;

@Entity
@Table(name = "question_ratings")
@NoArgsConstructor
@Getter
public class QuestionRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, foreignKeyDefinition = "FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE"))
    private QuestionEntity question;

    private boolean isPositive;

    private String comment;

    public QuestionRating toDomain() {
        return new QuestionRating(id, question.getId(), isPositive, comment);
    }

    public static QuestionRatingEntity fromDomain(QuestionRating rating, QuestionEntity question) {
        QuestionRatingEntity entity = new QuestionRatingEntity();
        entity.id = rating.getId();
        entity.question = question;
        entity.isPositive = rating.isPositive();
        entity.comment = rating.getComment();
        return entity;
    }
}