package pl.sliepov.egzamin.infrastructure.web.discipline.dto;

public record DisciplineMergeResponseDto(
        Long sourceDisciplineId,
        Long targetDisciplineId,
        String message) {
}