package pl.sliepov.egzamin.domain.model.test;

import java.time.LocalDateTime;
import java.util.List;

public class Test {
    private Long id;
    private String studentName;
    private String studentEmail;
    private List<Long> includedDisciplineIds;
    private List<Long> excludedDisciplineIds;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private TestStatus status;
    private Integer numberOfQuestions;

    public Test(Long id, String studentName, String studentEmail,
            List<Long> includedDisciplineIds, List<Long> excludedDisciplineIds,
            LocalDateTime startTime, LocalDateTime endTime, Integer score, TestStatus status,
            Integer numberOfQuestions) {
        this.id = id;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.includedDisciplineIds = includedDisciplineIds;
        this.excludedDisciplineIds = excludedDisciplineIds;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.status = status;
        this.numberOfQuestions = numberOfQuestions;
    }

    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public List<Long> getIncludedDisciplineIds() {
        return includedDisciplineIds;
    }

    public List<Long> getExcludedDisciplineIds() {
        return excludedDisciplineIds;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Integer getScore() {
        return score;
    }

    public TestStatus getStatus() {
        return status;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }
}