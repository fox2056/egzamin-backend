package pl.sliepov.egzamin.infrastructure.web.question.dto;

public record RatingResponseDto(
        Long questionId,
        boolean isPositive,
        String comment,
        String message) {
}