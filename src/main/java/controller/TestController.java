package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.TestService;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TestController {
    private final TestService service;

    @GetMapping("/test")
    public String test() {
        return service.getInitialText();
    }
}
