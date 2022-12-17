package test_environment.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TestData {
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("name")
    private final String name;
}
