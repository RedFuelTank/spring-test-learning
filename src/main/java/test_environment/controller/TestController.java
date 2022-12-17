package test_environment.controller;

import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import test_environment.model.TestData;
import test_environment.service.TestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TestController {
    private final TestService service;

    @GetMapping("/hello")
    public String test() {
        return service.getInitialText();
    }

    @GetMapping("/data/{id}")
    public TestData getDataById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping("/data")
    public List<TestData> findAll() {
        return service.findAll();
    }
}
