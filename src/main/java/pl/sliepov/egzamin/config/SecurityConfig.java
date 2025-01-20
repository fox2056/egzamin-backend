package pl.sliepov.egzamin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Value("${server.servlet.context-path:/egzaminator-backend}")
        private String contextPath;

        private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

        public SecurityConfig(OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService) {
                this.oidcUserService = oidcUserService;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configure(http))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(contextPath + "/",
                                                                contextPath + "/error",
                                                                contextPath + "/api/auth/**")
                                                .permitAll()
                                                .requestMatchers(contextPath + "/actuator/health/**").permitAll()
                                                .requestMatchers(contextPath + "/actuator/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .userInfoEndpoint(userInfo -> userInfo
                                                                .oidcUserService(oidcUserService))
                                                .defaultSuccessUrl("${app.frontend.url}", true))
                                .exceptionHandling(e -> e
                                                .authenticationEntryPoint(
                                                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                                .logout(logout -> logout
                                                .logoutUrl("/api/auth/logout")
                                                .logoutSuccessUrl("http://localhost:5173")
                                                .clearAuthentication(true)
                                                .invalidateHttpSession(true)
                                                .permitAll());
                return http.build();
        }
}