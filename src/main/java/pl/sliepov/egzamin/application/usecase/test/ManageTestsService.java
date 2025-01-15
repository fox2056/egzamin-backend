package pl.sliepov.egzamin.application.usecase.test;

import org.springframework.stereotype.Service;
import pl.sliepov.egzamin.domain.model.question.Question;
import pl.sliepov.egzamin.domain.model.test.Test;
import pl.sliepov.egzamin.domain.model.test.TestStatus;
import pl.sliepov.egzamin.domain.port.out.QuestionRepository;
import pl.sliepov.egzamin.domain.port.out.TestRepository;
import pl.sliepov.egzamin.infrastructure.web.test.TestAnswerDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Service
public class ManageTestsService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    public ManageTestsService(TestRepository testRepository, QuestionRepository questionRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

    public Test startTest(String studentName, String studentEmail,
            List<Long> includedDisciplineIds, List<Long> excludedDisciplineIds,
            Integer numberOfQuestions) {
        Test test = new Test(
                null,
                studentName,
                studentEmail,
                includedDisciplineIds,
                excludedDisciplineIds,
                LocalDateTime.now(),
                null,
                0,
                TestStatus.IN_PROGRESS,
                numberOfQuestions);
        return testRepository.save(test);
    }

    public List<Test> getTestsByEmail(String email) {
        return testRepository.findByStudentEmail(email);
    }

    public Test getTest(Long id) {
        return testRepository.findById(id);
    }

    public Test completeTest(Long id, int score) {
        Test test = testRepository.findById(id);
        Test completedTest = new Test(
                test.getId(),
                test.getStudentName(),
                test.getStudentEmail(),
                test.getIncludedDisciplineIds(),
                test.getExcludedDisciplineIds(),
                test.getStartTime(),
                LocalDateTime.now(),
                score,
                TestStatus.COMPLETED,
                test.getNumberOfQuestions());
        return testRepository.save(completedTest);
    }

    public TestQuestionsResult getQuestionsForTest(Long testId) {
        Test test = testRepository.findById(testId);
        if (test.getStatus() != TestStatus.IN_PROGRESS) {
            throw new IllegalStateException("Test nie jest w trakcie");
        }

        // Pobierz pytania dla każdej dyscypliny
        Map<Long, List<Question>> questionsByDiscipline = test.getIncludedDisciplineIds().stream()
                .collect(Collectors.toMap(
                        disciplineId -> disciplineId,
                        disciplineId -> questionRepository.findAllByDisciplineId(disciplineId).stream()
                                .filter(q -> !test.getExcludedDisciplineIds().contains(q.getDisciplineId()))
                                .collect(Collectors.toList())));

        // Oblicz całkowitą liczbę dostępnych pytań
        int totalAvailable = questionsByDiscipline.values().stream()
                .mapToInt(List::size)
                .sum();

        String message = null;
        boolean hasWarning = false;

        if (totalAvailable < test.getNumberOfQuestions()) {
            message = String.format(
                    "Uwaga: Dostępnych jest tylko %d pytań z wybranych dyscyplin, " +
                            "zamiast żądanych %d pytań.",
                    totalAvailable, test.getNumberOfQuestions());
            hasWarning = true;
        }

        // Oblicz ile pytań powinno być z każdej dyscypliny
        Map<Long, Integer> questionsPerDiscipline = calculateQuestionsPerDiscipline(
                questionsByDiscipline,
                test.getNumberOfQuestions());

        // Wybierz losowo pytania z każdej dyscypliny
        List<Question> selectedQuestions = questionsPerDiscipline.entrySet().stream()
                .flatMap(entry -> selectRandomQuestions(
                        questionsByDiscipline.get(entry.getKey()),
                        entry.getValue()).stream())
                .collect(Collectors.toList());

        return new TestQuestionsResult(selectedQuestions, message, hasWarning);
    }

    private Map<Long, Integer> calculateQuestionsPerDiscipline(
            Map<Long, List<Question>> questionsByDiscipline,
            int totalQuestionsNeeded) {

        // Najpierw sprawdź dostępność pytań w każdej dyscyplinie
        Map<Long, Integer> availableQuestions = questionsByDiscipline.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().size()));

        // Oblicz całkowitą liczbę dostępnych pytań
        int totalAvailable = availableQuestions.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        // Jeśli nie ma wystarczającej liczby pytań, użyj wszystkich dostępnych
        int questionsToDistribute = Math.min(totalQuestionsNeeded, totalAvailable);

        // Rozdziel pytania proporcjonalnie
        Map<Long, Integer> result = new HashMap<>();
        int remainingQuestions = questionsToDistribute;
        int remainingDisciplines = questionsByDiscipline.size();

        for (Map.Entry<Long, Integer> entry : availableQuestions.entrySet()) {
            if (remainingDisciplines == 1) {
                // Ostatnia dyscyplina dostaje wszystkie pozostałe pytania
                result.put(entry.getKey(), Math.min(remainingQuestions, entry.getValue()));
                break;
            }

            // Oblicz bazową liczbę pytań dla tej dyscypliny
            int baseQuestions = remainingQuestions / remainingDisciplines;
            // Nie możemy wziąć więcej pytań niż jest dostępnych
            int actualQuestions = Math.min(baseQuestions, entry.getValue());

            result.put(entry.getKey(), actualQuestions);
            remainingQuestions -= actualQuestions;
            remainingDisciplines--;
        }

        return result;
    }

    private List<Question> selectRandomQuestions(List<Question> questions, int count) {
        if (questions.size() <= count) {
            return new ArrayList<>(questions);
        }

        List<Question> shuffled = new ArrayList<>(questions);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, count);
    }

    public Test submitAnswers(Long testId, List<TestAnswerDto> answers) {
        Test test = testRepository.findById(testId);
        if (test.getStatus() != TestStatus.IN_PROGRESS) {
            throw new IllegalStateException("Test nie jest w trakcie");
        }

        // Pobierz pytania i ich poprawne odpowiedzi
        Map<Long, Question> questions = answers.stream()
                .map(a -> questionRepository.findById(a.questionId()))
                .collect(Collectors.toMap(Question::getId, q -> q));

        // Oblicz wynik
        int score = calculateScore(answers, questions);

        // Zakończ test
        return completeTest(testId, score);
    }

    private int calculateScore(List<TestAnswerDto> answers, Map<Long, Question> questions) {
        int correctAnswers = 0;
        for (TestAnswerDto answer : answers) {
            Question question = questions.get(answer.questionId());
            if (isAnswerCorrect(question, answer.selectedAnswers())) {
                correctAnswers++;
            }
        }
        return (correctAnswers * 100) / answers.size(); // Wynik w procentach
    }

    private boolean isAnswerCorrect(Question question, List<String> selectedAnswers) {
        return question.getCorrectAnswers().containsAll(selectedAnswers) &&
                selectedAnswers.containsAll(question.getCorrectAnswers());
    }
}