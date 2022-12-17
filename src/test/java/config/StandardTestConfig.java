package config;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;
import java.util.Properties;

public final class StandardTestConfig implements TestConfig {
    private StandardTestConfig() {}

    public static SimpleMappingExceptionResolver getExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        setExceptionMappings(resolver);
        setStatusCodeMappings(resolver);

        return resolver;
    }

    private static void setExceptionMappings(SimpleMappingExceptionResolver resolver) {
        Properties exceptionMappings = new Properties();
        exceptionMappings.put("exceptions.NotFoundException", "error/404");
        exceptionMappings.put("java.lang.Exception", "error");

        resolver.setExceptionMappings(exceptionMappings);
    }

    private static void setStatusCodeMappings(SimpleMappingExceptionResolver resolver) {
        Properties statusCodes = new Properties();
        statusCodes.put("/error/404", "404");
        statusCodes.put("/error", "500");

        resolver.setStatusCodes(statusCodes);
    }

    public static LocaleResolver getLocaleResolver() {
        return new FixedLocaleResolver(Locale.ENGLISH);
    }

    public static ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");

        return resolver;
    }


}
