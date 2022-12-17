package test_environment.controller;

import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test_environment.model.TestData;
import test_environment.service.TestService;

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
        return service.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping("/error/404")
    public void exception404() {
        throw new NotFoundException();
    }
    @GetMapping("/error")
    public void exception() throws Exception {
        throw new Exception();
    }
}
