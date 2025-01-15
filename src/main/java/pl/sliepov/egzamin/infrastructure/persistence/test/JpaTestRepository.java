package pl.sliepov.egzamin.infrastructure.persistence.test;

import org.springframework.stereotype.Repository;
import pl.sliepov.egzamin.domain.model.test.Test;
import pl.sliepov.egzamin.domain.port.out.TestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaTestRepository implements TestRepository {
    private final SpringDataTestRepository testRepository;

    public JpaTestRepository(SpringDataTestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Test save(Test test) {
        TestEntity entity = TestEntity.fromDomain(test);
        TestEntity saved = testRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Test findById(Long id) {
        return testRepository.findById(id)
                .map(TestEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Test not found"));
    }

    @Override
    public List<Test> findByStudentEmail(String email) {
        return testRepository.findByStudentEmail(email).stream()
                .map(TestEntity::toDomain)
                .collect(Collectors.toList());
    }
}