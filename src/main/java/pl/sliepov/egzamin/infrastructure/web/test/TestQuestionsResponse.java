package pl.sliepov.egzamin.infrastructure.web.test;

import pl.sliepov.egzamin.infrastructure.web.question.QuestionDto;

import java.util.List;

public record TestQuestionsResponse(
        List<QuestionDto> questions,
        String message,
        boolean hasWarning) {
}