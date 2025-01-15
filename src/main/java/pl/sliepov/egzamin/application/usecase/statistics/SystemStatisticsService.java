package pl.sliepov.egzamin.application.usecase.statistics;

import org.springframework.stereotype.Service;
import pl.sliepov.egzamin.domain.model.discipline.Discipline;
import pl.sliepov.egzamin.domain.model.test.TestStatus;
import pl.sliepov.egzamin.domain.port.out.DisciplineRepository;
import pl.sliepov.egzamin.domain.port.out.QuestionRepository;
import pl.sliepov.egzamin.domain.port.out.TestRepository;
import pl.sliepov.egzamin.infrastructure.web.statistics.dto.DisciplineStatisticsDto;
import pl.sliepov.egzamin.infrastructure.web.statistics.dto.SystemStatisticsDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SystemStatisticsService {
    private final DisciplineRepository disciplineRepository;
    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    public SystemStatisticsService(
            DisciplineRepository disciplineRepository,
            QuestionRepository questionRepository,
            TestRepository testRepository) {
        this.disciplineRepository = disciplineRepository;
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    public SystemStatisticsDto getSystemStatistics() {
        List<Discipline> disciplines = disciplineRepository.findAll();

        Map<String, DisciplineStatisticsDto> disciplineStats = disciplines.stream()
                .collect(Collectors.toMap(
                        Discipline::getName,
                        discipline -> new DisciplineStatisticsDto(
                                discipline.getId(),
                                discipline.getName(),
                                discipline.getProfessor(),
                                questionRepository.findAllByDisciplineId(discipline.getId()).size())));

        int totalQuestions = disciplineStats.values().stream()
                .mapToInt(DisciplineStatisticsDto::questionCount)
                .sum();

        long completedTests = testRepository.countByStatus(TestStatus.COMPLETED);

        return new SystemStatisticsDto(disciplineStats, totalQuestions, (int) completedTests);
    }
}