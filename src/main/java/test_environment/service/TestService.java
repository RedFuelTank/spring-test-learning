package test_environment.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public String getInitialText() {
        return "Hello world!";
    }
}
