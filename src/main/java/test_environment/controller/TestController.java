package test_environment.controller;

import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/error/404")
    public void exception404() {
        throw new NotFoundException();
    }
    @GetMapping("/error")
    public void exception() throws Exception {
        throw new Exception();
    }
}
