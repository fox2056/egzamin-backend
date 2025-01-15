package pl.sliepov.egzamin.infrastructure.persistence.test;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sliepov.egzamin.domain.model.test.Test;
import pl.sliepov.egzamin.domain.model.test.TestStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tests")
@NoArgsConstructor
@Getter
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private String studentEmail;

    @ElementCollection
    @CollectionTable(name = "test_included_disciplines")
    private List<Long> includedDisciplineIds;

    @ElementCollection
    @CollectionTable(name = "test_excluded_disciplines")
    private List<Long> excludedDisciplineIds;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;

    @Enumerated(EnumType.STRING)
    private StatusEntity status;

    private Integer numberOfQuestions;

    public Test toDomain() {
        return new Test(id, studentName, studentEmail,
                includedDisciplineIds, excludedDisciplineIds,
                startTime, endTime, score, status.toDomain(), numberOfQuestions);
    }

    public static TestEntity fromDomain(Test test) {
        TestEntity entity = new TestEntity();
        entity.id = test.getId();
        entity.studentName = test.getStudentName();
        entity.studentEmail = test.getStudentEmail();
        entity.includedDisciplineIds = test.getIncludedDisciplineIds();
        entity.excludedDisciplineIds = test.getExcludedDisciplineIds();
        entity.startTime = test.getStartTime();
        entity.endTime = test.getEndTime();
        entity.score = test.getScore();
        entity.status = StatusEntity.fromDomain(test.getStatus());
        entity.numberOfQuestions = test.getNumberOfQuestions();
        return entity;
    }

    public enum StatusEntity {
        IN_PROGRESS, COMPLETED, CANCELLED;

        public static StatusEntity fromDomain(TestStatus status) {
            return StatusEntity.valueOf(status.name());
        }

        public TestStatus toDomain() {
            return TestStatus.valueOf(this.name());
        }
    }
}