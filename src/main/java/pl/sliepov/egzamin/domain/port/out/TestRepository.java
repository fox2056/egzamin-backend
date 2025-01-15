package pl.sliepov.egzamin.domain.port.out;

import pl.sliepov.egzamin.domain.model.test.Test;
import java.util.List;

public interface TestRepository {
    Test save(Test test);

    Test findById(Long id);

    List<Test> findByStudentEmail(String email);
}