package pl.sliepov.egzamin.infrastructure.web.question.dto;

public record CommentDto(
        String comment,
        boolean isPositive) {
}