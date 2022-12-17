package test_environment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test_environment.model.TestData;
import test_environment.service.TestService;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static config.StandardTestConfig.*;
class TestControllerTest {
    private RequestBuilder requestBuilder;
    private TestService service;

    @BeforeEach
    void configureSystemUnderTest() {
        service = mock(TestService.class);

        TestController testedController = new TestController(service);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testedController)
                .setMessageConverters(getObjectMapperHttpMessageConverter())
                .build();
        requestBuilder = new RequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("Render the information of the requested data")
    class FindById {

        private final Long DATA_ID = 4L;

        @Nested
        @DisplayName("When the requested data isn't found from the database")
        class WhenRequestedDataIsNotFound {

            @BeforeEach
            void serviceThrowsNotFoundException() {
                given(service.findById(DATA_ID))
                        .willReturn(Optional.ofNullable(null));
            }

            @Test
            @DisplayName("Should return the HTTP status code 404")
            void shouldReturnHttpStatusCodeNotFound() throws Exception {
                requestBuilder.findById(DATA_ID).andExpect(status().isNotFound());
            }

            @Test
            @DisplayName("Should return HTTP response which has an empty body")
            void shouldReturnHttpResponseWithEmptyBody() throws Exception {
                requestBuilder.findById(DATA_ID)
                        .andExpect(content().string(""));
            }
        }

        @Nested
        @DisplayName("When the requested data is found from the database")
        class WhenRequestedDataIsFound {
            private final String DATA_NAME = "Test";

            @BeforeEach
            void serviceReturnsData() {
                TestData data = new TestData(DATA_ID, DATA_NAME);

                given(service.findById(DATA_ID))
                        .willReturn(Optional.ofNullable(data));
            }

            @Test
            @DisplayName("Should return the HTTP status code 200")
            void shouldReturnHttpStatusOk() throws Exception {
                requestBuilder.findById(DATA_ID)
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("Should return correct content type data")
            void shouldDisplayItemWithCorrectContentType() throws Exception {
                requestBuilder.findById(DATA_ID)
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
            }

            @Test
            @DisplayName("Should return correct data")
            void shouldReturnCorrectData() throws Exception {
                requestBuilder.findById(DATA_ID)
                        .andExpect(jsonPath("$.id", equalTo(DATA_ID.intValue())))
                        .andExpect(jsonPath("$.name", equalTo(DATA_NAME)));
            }

        }
    }
}