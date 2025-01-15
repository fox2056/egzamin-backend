package pl.sliepov.egzamin.infrastructure.web.question;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sliepov.egzamin.application.usecase.question.ManageQuestionsService;
import pl.sliepov.egzamin.domain.model.question.Question;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final ManageQuestionsService manageQuestionsService;

    public QuestionController(ManageQuestionsService manageQuestionsService) {
        this.manageQuestionsService = manageQuestionsService;
    }

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto questionDto) {
        Question question = manageQuestionsService.createQuestion(
                questionDto.content(),
                questionDto.type(),
                questionDto.correctAnswers(),
                questionDto.incorrectAnswers(),
                questionDto.disciplineId());
        return ResponseEntity.ok(toDto(question));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable Long id) {
        Question question = manageQuestionsService.getQuestion(id);
        return ResponseEntity.ok(toDto(question));
    }

    @GetMapping("/discipline/{disciplineId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByDiscipline(@PathVariable Long disciplineId) {
        List<Question> questions = manageQuestionsService.getQuestionsByDiscipline(disciplineId);
        List<QuestionDto> dtos = questions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/{questionId}/discipline")
    public ResponseEntity<QuestionDto> changeQuestionDiscipline(
            @PathVariable Long questionId,
            @RequestParam Long newDisciplineId) {
        Question updatedQuestion = manageQuestionsService.changeQuestionDiscipline(questionId, newDisciplineId);
        return ResponseEntity.ok(toDto(updatedQuestion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        manageQuestionsService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
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