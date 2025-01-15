package pl.sliepov.egzamin.infrastructure.web.question.dto;

public record DeleteQuestionResponseDto(
        Long questionId,
        String message,
        boolean success) {
}