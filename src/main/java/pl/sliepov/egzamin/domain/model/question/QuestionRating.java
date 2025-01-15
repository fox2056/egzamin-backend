package pl.sliepov.egzamin.domain.model.question;

public class QuestionRating {
    private Long id;
    private Long questionId;
    private boolean isPositive;
    private String comment; // opcjonalny komentarz do oceny

    public QuestionRating(Long id, Long questionId, boolean isPositive, String comment) {
        this.id = id;
        this.questionId = questionId;
        this.isPositive = isPositive;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public String getComment() {
        return comment;
    }
}