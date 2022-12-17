package test_environment.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test_environment.service.TestService;

import static org.mockito.Mockito.mock;
import static config.StandardTestConfig.*;

class TestControllerTest {
    private RequestBuilder requestBuilder;
    private TestService service;

    @BeforeEach
    void configureSystemUnderTest() {
        service = mock(TestService.class);

        TestController testedController = new TestController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testedController)
                .setHandlerExceptionResolvers(getExceptionResolver())
                .setLocaleResolver(getLocaleResolver())
                .setViewResolvers(getViewResolver())
                .build();
        requestBuilder = new RequestBuilder(mockMvc);
    }



}