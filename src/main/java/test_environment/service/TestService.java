package test_environment.service;

import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test_environment.model.TestData;
import test_environment.repository.TestRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository repository;

    public String getInitialText() {
        return "Hello world!";
    }

    public Optional<TestData> findById(Long id) {
        return repository.getById(id);
    }
}
