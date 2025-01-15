package pl.sliepov.egzamin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sliepov.egzamin.application.usecase.discipline.ManageDisciplinesService;
import pl.sliepov.egzamin.application.usecase.question.ManageQuestionsService;
import pl.sliepov.egzamin.domain.port.out.DisciplineRepository;
import pl.sliepov.egzamin.domain.port.out.QuestionRepository;
import pl.sliepov.egzamin.domain.port.out.QuestionRatingRepository;

@Configuration
public class ApplicationConfig {

    @Bean
    public ManageDisciplinesService manageDisciplinesService(
            DisciplineRepository disciplineRepository,
            QuestionRepository questionRepository) {
        return new ManageDisciplinesService(disciplineRepository, questionRepository);
    }

    @Bean
    public ManageQuestionsService manageQuestionsService(
            QuestionRepository questionRepository,
            QuestionRatingRepository questionRatingRepository,
            DisciplineRepository disciplineRepository) {
        return new ManageQuestionsService(
                questionRepository,
                questionRatingRepository,
                disciplineRepository);
    }
}
