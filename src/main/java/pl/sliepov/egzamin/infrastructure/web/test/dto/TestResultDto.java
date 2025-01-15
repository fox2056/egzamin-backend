package pl.sliepov.egzamin.infrastructure.web.test.dto;

import java.util.List;

public record TestResultDto(
        int score,
        int totalQuestions,
        int correctAnswers,
        List<QuestionResultDto> questionResults) {
}