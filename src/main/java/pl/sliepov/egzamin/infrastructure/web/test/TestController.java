package pl.sliepov.egzamin.infrastructure.web.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sliepov.egzamin.infrastructure.web.question.QuestionDto;
import pl.sliepov.egzamin.application.usecase.test.ManageTestsService;
import pl.sliepov.egzamin.application.usecase.test.TestQuestionsResult;
import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.domain.model.test.Test;
import pl.sliepov.egzamin.infrastructure.web.test.dto.TestQuestionDto;
import pl.sliepov.egzamin.infrastructure.web.test.dto.TestQuestionsResponse;
import pl.sliepov.egzamin.infrastructure.web.test.dto.TestResultDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
public class TestController {
    private final ManageTestsService testService;

    public TestController(ManageTestsService testService) {
        this.testService = testService;
    }

    @PostMapping
    public ResponseEntity<Test> startTest(@RequestBody StartTestDto startTestDto) {
        startTestDto.validate();
        Test test = testService.startTest(
                startTestDto.studentName(),
                startTestDto.studentEmail(),
                startTestDto.includedDisciplineIds(),
                startTestDto.excludedDisciplineIds(),
                startTestDto.numberOfQuestions());
        return ResponseEntity.ok(test);
    }

    @GetMapping("/student/{email}")
    public ResponseEntity<List<Test>> getTestsByEmail(@PathVariable String email) {
        List<Test> tests = testService.getTestsByEmail(email);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getTest(@PathVariable Long id) {
        Test test = testService.getTest(id);
        return ResponseEntity.ok(test);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<TestQuestionsResponse> getTestQuestions(@PathVariable Long id) {
        TestQuestionsResult result = testService.getQuestionsForTest(id);

        List<TestQuestionDto> questionDtos = result.questions().stream()
                .map(TestQuestionDto::fromQuestion)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new TestQuestionsResponse(
                questionDtos,
                result.message(),
                result.hasWarning()));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<TestResultDto> submitTest(
            @PathVariable Long id,
            @RequestBody List<TestAnswerDto> answers) {
        TestResultDto result = testService.submitAnswers(id, answers);
        return ResponseEntity.ok(result);
    }

    private QuestionDto toQuestionDto(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getContent(),
                question.getType(),
                question.getCorrectAnswers(),
                question.getIncorrectAnswers(),
                question.getDisciplineId());
    }
}