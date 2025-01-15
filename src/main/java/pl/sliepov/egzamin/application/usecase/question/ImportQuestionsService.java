package pl.sliepov.egzamin.application.usecase.question;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sliepov.egzamin.application.usecase.discipline.ManageDisciplinesService;
import pl.sliepov.egzamin.infrastructure.web.question.QuestionImportDto;
import pl.sliepov.egzamin.domain.model.discipline.Discipline;
import pl.sliepov.egzamin.domain.model.question.Question;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImportQuestionsService {
    private final ManageQuestionsService manageQuestionsService;
    private final ManageDisciplinesService manageDisciplinesService;

    public ImportQuestionsService(
            ManageQuestionsService manageQuestionsService,
            ManageDisciplinesService manageDisciplinesService) {
        this.manageQuestionsService = manageQuestionsService;
        this.manageDisciplinesService = manageDisciplinesService;
    }

    @Transactional
    public List<Question> importQuestions(List<QuestionImportDto> questions) {
        // Walidacja wszystkich pytań przed importem
        questions.forEach(QuestionImportDto::validate);

        // Zbierz wszystkie unikalne nazwy dyscyplin
        Set<String> disciplineNames = questions.stream()
                .map(QuestionImportDto::disciplineName)
                .collect(Collectors.toSet());

        // Pobierz lub utwórz dyscypliny
        Map<String, Discipline> disciplineMap = getDisciplines(disciplineNames);

        // Importuj pytania
        return questions.stream()
                .map(q -> importQuestion(q, disciplineMap.get(q.disciplineName())))
                .collect(Collectors.toList());
    }

    private Map<String, Discipline> getDisciplines(Set<String> names) {
        return names.stream()
                .map(name -> manageDisciplinesService.findByName(name)
                        .orElseGet(() -> manageDisciplinesService.createDiscipline(name, "TBD")))
                .collect(Collectors.toMap(
                        Discipline::getName,
                        discipline -> discipline));
    }

    private Question importQuestion(QuestionImportDto dto, Discipline discipline) {
        return manageQuestionsService.createQuestion(
                dto.content(),
                dto.type(),
                dto.correctAnswers(),
                dto.incorrectAnswers(),
                discipline.getId());
    }
}