package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public final class StandardTestConfig implements TestConfig {
    private StandardTestConfig() {}

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public static MappingJackson2HttpMessageConverter getObjectMapperHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(getObjectMapper());
        return converter;
    }


}
