package pl.sliepov.egzamin.infrastructure.web.statistics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sliepov.egzamin.application.usecase.statistics.SystemStatisticsService;
import pl.sliepov.egzamin.infrastructure.web.statistics.dto.SystemStatisticsDto;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final SystemStatisticsService statisticsService;

    public StatisticsController(SystemStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<SystemStatisticsDto> getSystemStatistics() {
        return ResponseEntity.ok(statisticsService.getSystemStatistics());
    }
}