package pl.sliepov.egzamin.infrastructure.web.statistics.dto;

import java.util.Map;

public record SystemStatisticsDto(
        Map<String, DisciplineStatisticsDto> disciplineStatistics,
        int totalQuestions,
        int completedTests) {
}