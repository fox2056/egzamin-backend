package pl.sliepov.egzamin.infrastructure.web.question.dto;

import java.util.List;

public record QuestionRatingStatsDto(
                long positiveCount,
                long negativeCount,
                List<CommentDto> recentComments) {
}