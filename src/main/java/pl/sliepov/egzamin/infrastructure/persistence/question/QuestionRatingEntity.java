package pl.sliepov.egzamin.infrastructure.persistence.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sliepov.egzamin.domain.model.question.QuestionRating;

@Entity
@Table(name = "question_rating")
@NoArgsConstructor
@Getter
public class QuestionRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
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