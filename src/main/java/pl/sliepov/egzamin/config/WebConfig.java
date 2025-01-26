package pl.sliepov.egzamin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Konfiguracja CORS dla API
        CorsConfiguration apiCorsConfig = new CorsConfiguration();
        apiCorsConfig.setAllowCredentials(true);
        apiCorsConfig.addAllowedOrigin("*");
        apiCorsConfig.addAllowedHeader("*");
        apiCorsConfig.addAllowedMethod("*");
        apiCorsConfig.addExposedHeader("Content-Disposition");
        source.registerCorsConfiguration("/api/**", apiCorsConfig);

        // Konfiguracja CORS dla Actuatora
        CorsConfiguration actuatorCorsConfig = new CorsConfiguration();
        actuatorCorsConfig.addAllowedOrigin("*");
        actuatorCorsConfig.addAllowedHeader("*");
        actuatorCorsConfig.addAllowedMethod("*");
        source.registerCorsConfiguration("/actuator/**", actuatorCorsConfig);

        return new CorsFilter(source);
    }
}