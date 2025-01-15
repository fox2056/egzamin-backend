package pl.sliepov.egzamin.infrastructure.web.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sliepov.egzamin.application.usecase.question.ImportQuestionsService;
import pl.sliepov.egzamin.domain.model.question.Question;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions/import")
public class QuestionImportController {
    private final ImportQuestionsService importQuestionsService;
    private final ObjectMapper objectMapper;

    public QuestionImportController(ImportQuestionsService importQuestionsService, ObjectMapper objectMapper) {
        this.importQuestionsService = importQuestionsService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<QuestionDto>> importQuestionsFile(@RequestParam("file") MultipartFile file) {
        try {
            List<QuestionImportDto> questions = Arrays.asList(
                    objectMapper.readValue(file.getInputStream(), QuestionImportDto[].class));

            List<Question> importedQuestions = importQuestionsService.importQuestions(questions);
            return ResponseEntity.ok(importedQuestions.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas przetwarzania pliku JSON", e);
        }
    }

    private QuestionDto toDto(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getContent(),
                question.getType(),
                question.getCorrectAnswers(),
                question.getIncorrectAnswers(),
                question.getDisciplineId());
    }
}