package pl.sliepov.egzamin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${server.servlet.context-path:/egzaminator-backend}")
    private String contextPath;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping(contextPath + "/api/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Set-Cookie");

        registry.addMapping(contextPath + "/actuator/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}