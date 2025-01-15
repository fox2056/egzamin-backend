package pl.sliepov.egzamin.infrastructure.web.question;

import pl.sliepov.egzamin.domain.model.question.QuestionType;
import java.util.List;

public record QuestionImportDto(
        String disciplineName,
        String content,
        QuestionType type,
        List<String> correctAnswers,
        List<String> incorrectAnswers) {
    public void validate() {
        if (disciplineName == null || disciplineName.isBlank()) {
            throw new IllegalArgumentException("Nazwa dyscypliny jest wymagana");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Treść pytania jest wymagana");
        }
        if (type == null) {
            throw new IllegalArgumentException("Typ pytania jest wymagany");
        }
        if (correctAnswers == null || correctAnswers.isEmpty()) {
            throw new IllegalArgumentException("Wymagana jest co najmniej jedna poprawna odpowiedź");
        }
        if (incorrectAnswers == null || incorrectAnswers.isEmpty()) {
            throw new IllegalArgumentException("Wymagana jest co najmniej jedna niepoprawna odpowiedź");
        }
    }
}