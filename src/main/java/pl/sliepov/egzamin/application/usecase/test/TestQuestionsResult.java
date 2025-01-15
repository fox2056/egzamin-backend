package pl.sliepov.egzamin.application.usecase.test;

import pl.sliepov.egzamin.domain.model.question.Question;
import java.util.List;

public record TestQuestionsResult(
        List<Question> questions,
        String message,
        boolean hasWarning) {
}