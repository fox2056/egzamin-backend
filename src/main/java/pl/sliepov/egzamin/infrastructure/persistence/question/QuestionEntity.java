package pl.sliepov.egzamin.infrastructure.persistence.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.domain.model.question.QuestionType;
import pl.sliepov.egzamin.infrastructure.persistence.discipline.DisciplineEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@NoArgsConstructor
@Getter
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @ElementCollection
    @CollectionTable(name = "question_correct_answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "answer")
    private List<String> correctAnswers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "question_incorrect_answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "answer")
    private List<String> incorrectAnswers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private DisciplineEntity discipline;

    public Question toDomain() {
        return new Question(
                id,
                content,
                type,
                correctAnswers,
                incorrectAnswers,
                discipline.getId());
    }

    public static QuestionEntity fromDomain(Question question, DisciplineEntity discipline) {
        QuestionEntity entity = new QuestionEntity();
        entity.id = question.getId();
        entity.content = question.getContent();
        entity.type = question.getType();
        entity.correctAnswers = new ArrayList<>(question.getCorrectAnswers());
        entity.incorrectAnswers = new ArrayList<>(question.getIncorrectAnswers());
        entity.discipline = discipline;
        return entity;
    }
}