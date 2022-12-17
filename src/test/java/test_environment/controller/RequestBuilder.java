package test_environment.controller;

import org.springframework.test.web.servlet.MockMvc;

public class RequestBuilder {
    private final MockMvc mockMvc;

    public RequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
}
