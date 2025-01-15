package pl.sliepov.egzamin.domain.model.question;

import java.util.List;

public class Question {
    private Long id;
    private String content;
    private QuestionType type;
    private List<String> correctAnswers;
    private List<String> incorrectAnswers;
    private Long disciplineId;

    public Question(Long id, String content, QuestionType type, List<String> correctAnswers,
            List<String> incorrectAnswers, Long disciplineId) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
        this.disciplineId = disciplineId;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public QuestionType getType() {
        return type;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }
}