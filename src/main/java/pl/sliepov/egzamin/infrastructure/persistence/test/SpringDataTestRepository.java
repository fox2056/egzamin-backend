package pl.sliepov.egzamin.infrastructure.persistence.test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTestRepository extends JpaRepository<TestEntity, Long> {
    List<TestEntity> findByStudentEmail(String email);

    long countByStatus(TestEntity.StatusEntity status);
}