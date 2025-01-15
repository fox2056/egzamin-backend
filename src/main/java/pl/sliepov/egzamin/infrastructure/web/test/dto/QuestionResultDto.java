package pl.sliepov.egzamin.infrastructure.web.test.dto;

import java.util.List;

public record QuestionResultDto(
        Long questionId,
        String content,
        List<String> correctAnswers,
        List<String> selectedAnswers,
        boolean isCorrect) {
}