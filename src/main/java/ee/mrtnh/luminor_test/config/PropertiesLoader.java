package ee.mrtnh.luminor_test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
        @PropertySource("/properties/application.properties"),
        @PropertySource("/properties/actuator.properties"),
        @PropertySource("/properties/db.properties"),
        @PropertySource("/properties/notification.properties")
})
public class PropertiesLoader {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
