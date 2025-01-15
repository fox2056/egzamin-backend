package pl.sliepov.egzamin.infrastructure.web.statistics.dto;

public record DisciplineStatisticsDto(
        Long disciplineId,
        String name,
        String professor,
        int questionCount) {
}