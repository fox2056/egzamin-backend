package pl.sliepov.egzamin.infrastructure.web.test.dto;

import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.domain.model.question.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record TestQuestionDto(
        Long id,
        String content,
        QuestionType type,
        List<String> answers) {
    public static TestQuestionDto fromQuestion(Question question) {
        // Łączymy wszystkie odpowiedzi i mieszamy je
        List<String> allAnswers = new ArrayList<>();
        allAnswers.addAll(question.getCorrectAnswers());
        allAnswers.addAll(question.getIncorrectAnswers());
        Collections.shuffle(allAnswers);

        return new TestQuestionDto(
                question.getId(),
                question.getContent(),
                question.getType(),
                allAnswers);
    }
}