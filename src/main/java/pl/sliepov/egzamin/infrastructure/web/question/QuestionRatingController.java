package pl.sliepov.egzamin.infrastructure.web.question;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sliepov.egzamin.application.usecase.question.ManageQuestionRatingsService;
import pl.sliepov.egzamin.application.usecase.question.ManageQuestionRatingsService.RatingStats;

@RestController
@RequestMapping("/api/questions/{questionId}/ratings")
public class QuestionRatingController {
    private final ManageQuestionRatingsService ratingService;

    public QuestionRatingController(ManageQuestionRatingsService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Void> rateQuestion(
            @PathVariable Long questionId,
            @RequestParam boolean isPositive,
            @RequestParam(required = false) String comment) {
        ratingService.rateQuestion(questionId, isPositive, comment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<RatingStats> getQuestionRatingStats(@PathVariable Long questionId) {
        RatingStats stats = ratingService.getQuestionRatingStats(questionId);
        return ResponseEntity.ok(stats);
    }
}