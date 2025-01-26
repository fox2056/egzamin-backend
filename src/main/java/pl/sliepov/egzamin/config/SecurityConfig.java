package pl.sliepov.egzamin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Value("${server.servlet.context-path:/egzaminator-backend}")
        private String contextPath;

        private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService;

        public SecurityConfig(OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService) {
                this.oauth2UserService = oauth2UserService;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configure(http))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                new AntPathRequestMatcher(contextPath + "/"),
                                                                new AntPathRequestMatcher(contextPath + "/error"),
                                                                new AntPathRequestMatcher(contextPath + "/api/auth/**"))
                                                .permitAll()
                                                .requestMatchers(new AntPathRequestMatcher(
                                                                contextPath + "/actuator/health/**"))
                                                .permitAll()
                                                .requestMatchers(
                                                                new AntPathRequestMatcher(contextPath + "/actuator/**"))
                                                .hasRole("ADMIN")
                                                .anyRequest()
                                                .authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .userInfoEndpoint(userInfo -> userInfo
                                                                .userService(oauth2UserService))
                                                .defaultSuccessUrl(System.getenv("EGZAMINATOR_FRONT_END_URL"), true))
                                .exceptionHandling(e -> e
                                                .authenticationEntryPoint(
                                                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                                .logout(logout -> logout
                                                .logoutUrl("/api/auth/logout")
                                                .logoutSuccessUrl(System.getenv("EGZAMINATOR_FRONT_END_URL"))
                                                .clearAuthentication(true)
                                                .invalidateHttpSession(true)
                                                .permitAll());
                return http.build();
        }
}