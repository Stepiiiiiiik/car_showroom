package ru.gorlov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.gorlov.core.config.JPAConfig;
import ru.gorlov.core.config.KeycloakConfig;
import ru.gorlov.core.config.OpenApiConfig;
import ru.gorlov.core.config.SecurityConfig;

@SpringBootApplication
@Import({JPAConfig.class, KeycloakConfig.class, SecurityConfig.class, OpenApiConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
