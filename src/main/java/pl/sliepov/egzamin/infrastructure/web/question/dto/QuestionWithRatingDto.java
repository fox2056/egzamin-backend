package pl.sliepov.egzamin.infrastructure.web.question.dto;

import pl.sliepov.egzamin.domain.model.question.QuestionType;
import java.util.List;

public record QuestionWithRatingDto(
        Long id,
        String content,
        QuestionType type,
        List<String> correctAnswers,
        List<String> incorrectAnswers,
        Long disciplineId,
        QuestionRatingStatsDto ratings) {
} 