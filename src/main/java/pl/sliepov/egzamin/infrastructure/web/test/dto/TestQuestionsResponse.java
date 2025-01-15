package pl.sliepov.egzamin.infrastructure.web.test.dto;

import java.util.List;

public record TestQuestionsResponse(
        List<TestQuestionDto> questions,
        String message,
        boolean hasWarning) {
}