package pl.sliepov.egzamin.infrastructure.web.question;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sliepov.egzamin.application.usecase.question.ManageQuestionsService;
import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.infrastructure.web.question.dto.DeleteQuestionResponseDto;
import pl.sliepov.egzamin.infrastructure.web.question.dto.QuestionWithRatingDto;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final ManageQuestionsService manageQuestionsService;

    public QuestionController(ManageQuestionsService manageQuestionsService) {
        this.manageQuestionsService = manageQuestionsService;
    }

    @PostMapping
    public ResponseEntity<QuestionWithRatingDto> createQuestion(@RequestBody QuestionDto questionDto) {
        Question question = manageQuestionsService.createQuestion(
                questionDto.content(),
                questionDto.type(),
                questionDto.correctAnswers(),
                questionDto.incorrectAnswers(),
                questionDto.disciplineId());
        return ResponseEntity.ok(manageQuestionsService.getQuestionWithRatings(question.getId()));
    }

    @GetMapping("/discipline/{disciplineId}")
    public ResponseEntity<List<QuestionWithRatingDto>> getQuestionsByDiscipline(@PathVariable Long disciplineId) {
        List<Question> questions = manageQuestionsService.getQuestionsByDiscipline(disciplineId);
        List<QuestionWithRatingDto> dtos = questions.stream()
                .map(q -> manageQuestionsService.getQuestionWithRatings(q.getId()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/{questionId}/discipline")
    public ResponseEntity<QuestionWithRatingDto> changeQuestionDiscipline(
            @PathVariable Long questionId,
            @RequestParam Long newDisciplineId) {
        Question updatedQuestion = manageQuestionsService.changeQuestionDiscipline(questionId, newDisciplineId);
        return ResponseEntity.ok(manageQuestionsService.getQuestionWithRatings(updatedQuestion.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteQuestionResponseDto> deleteQuestion(@PathVariable Long id) {
        manageQuestionsService.deleteQuestion(id);
        DeleteQuestionResponseDto response = new DeleteQuestionResponseDto(
                id,
                "Pytanie zostało pomyślnie usunięte",
                true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionWithRatingDto> getQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(manageQuestionsService.getQuestionWithRatings(id));
    }
}