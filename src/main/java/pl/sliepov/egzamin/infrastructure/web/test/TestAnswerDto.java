package pl.sliepov.egzamin.infrastructure.web.test;

import java.util.List;

public record TestAnswerDto(
        Long questionId,
        List<String> selectedAnswers) {
}