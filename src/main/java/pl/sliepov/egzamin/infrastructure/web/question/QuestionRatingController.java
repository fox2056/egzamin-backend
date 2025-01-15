package pl.sliepov.egzamin.infrastructure.web.question;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sliepov.egzamin.application.usecase.question.ManageQuestionRatingsService;
import pl.sliepov.egzamin.application.usecase.question.ManageQuestionRatingsService.RatingStats;
import pl.sliepov.egzamin.infrastructure.web.question.dto.RatingRequestDto;
import pl.sliepov.egzamin.infrastructure.web.question.dto.RatingResponseDto;

@RestController
@RequestMapping("/api/questions/{questionId}/ratings")
public class QuestionRatingController {
    private final ManageQuestionRatingsService ratingService;

    public QuestionRatingController(ManageQuestionRatingsService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingResponseDto> rateQuestion(
            @PathVariable Long questionId,
            @RequestParam boolean isPositive,
            @RequestBody RatingRequestDto ratingRequest) {
        ratingService.rateQuestion(questionId, isPositive, ratingRequest.comment());

        RatingResponseDto response = new RatingResponseDto(
                questionId,
                isPositive,
                ratingRequest.comment(),
                "Ocena została dodana pomyślnie");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<RatingStats> getQuestionRatingStats(@PathVariable Long questionId) {
        RatingStats stats = ratingService.getQuestionRatingStats(questionId);
        return ResponseEntity.ok(stats);
    }
}