package test_environment.repository;

import org.springframework.stereotype.Repository;
import test_environment.model.TestData;

import java.util.Optional;

@Repository
public class TestRepository {
    public Optional<TestData> getById(Long id) {
        return Optional.ofNullable(switch (id.intValue()) {
            case 1 -> new TestData(1L, "First");
            case 2 -> new TestData(2L, "Second");
            case 3 -> new TestData(3L, "Third");
            default -> null;
        });
    }
}
