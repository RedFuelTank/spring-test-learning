package test_environment.controller;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class RequestBuilder {
    private final MockMvc mockMvc;

    public RequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions findById(Long id) throws Exception {
        return mockMvc.perform(get("/data/{id}", id));
    }

    public ResultActions initialState() throws Exception {
        return mockMvc.perform(get("/home"));
    }

}
