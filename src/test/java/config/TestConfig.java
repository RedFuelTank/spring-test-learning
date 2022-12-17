package config;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public interface TestConfig {
    class AbstractClassMethodInvokeException extends RuntimeException{
    }

    static SimpleMappingExceptionResolver getExceptionResolver() {
        throw new AbstractClassMethodInvokeException();
    }

    static LocaleResolver getLocaleResolver() {
        throw new AbstractClassMethodInvokeException();
    }

    static ViewResolver getViewResolver() {
        throw new AbstractClassMethodInvokeException();
    }

}
