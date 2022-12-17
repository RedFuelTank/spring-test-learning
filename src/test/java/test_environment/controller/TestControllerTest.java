package test_environment.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test_environment.model.TestData;
import test_environment.service.TestService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static config.StandardTestConfig.getObjectMapperHttpMessageConverter;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Nested
    @DisplayName("Find all data elements from database")
    class FindAll {

        @Test
        @DisplayName("Should return the HTTP status 200")
        void shouldReturnHttpStatusOk() throws Exception {
            requestBuilder.findAll()
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return HTTP response with JSON media-type")
        void shouldReturnCorrectMethodType() throws Exception {
            requestBuilder.findAll()
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Nested
        @DisplayName("When no elements are found")
        class WhenNoElementsAreFound {
            @BeforeEach
            void returnEmptyList() {
                given(service.findAll())
                        .willReturn(Collections.emptyList());
            }

            @Test
            @DisplayName("Should return empty list")
            void shouldReturnEmptyList() throws Exception {
                requestBuilder.findAll()
                        .andExpect(jsonPath("$", hasSize(0)));
            }
        }

        @Nested
        @DisplayName("When 2 elements are found")
        class When2ElementsAreFound {
            private static final Long FIRST_ELEMENT_ID = 1L;
            private static final String FIRST_ELEMENT_NAME = "First";

            private static final Long SECOND_ELEMENT_ID = 2L;
            private static final String SECOND_ELEMENT_NAME = "Second";


            @BeforeEach
            void returnListWith2Elements() {
                given(service.findAll()).willReturn(
                        List.of(
                                new TestData(FIRST_ELEMENT_ID, FIRST_ELEMENT_NAME),
                                new TestData(SECOND_ELEMENT_ID, SECOND_ELEMENT_NAME)
                        )
                );
            }

            @Test
            @DisplayName("Should return list which contains 2 elements")
            void shouldReturn2Elements() throws Exception {
                requestBuilder.findAll()
                        .andExpect(jsonPath("$", hasSize(2)));
            }

            @Test
            @DisplayName("Should return first element with correct data")
            void shouldReturnCorrectFirstElement() throws Exception {
                requestBuilder.findAll()
                        .andExpect(jsonPath("$[0].id", equalTo(FIRST_ELEMENT_ID.intValue())))
                        .andExpect(jsonPath("$[0].name", equalTo(FIRST_ELEMENT_NAME)));
            }

            @Test
            @DisplayName("Should return second element with correct data")
            void shouldReturnCorrectSecondElement() throws Exception {
                requestBuilder.findAll()
                        .andExpect(jsonPath("$[1].id", equalTo(SECOND_ELEMENT_ID.intValue())))
                        .andExpect(jsonPath("$[1].name", equalTo(SECOND_ELEMENT_NAME)));
            }
        }
    }
}