package pl.sliepov.egzamin.infrastructure.web.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import pl.sliepov.egzamin.application.usecase.user.UserService;
import pl.sliepov.egzamin.domain.model.user.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Map.of("authenticated", false);
        }

        User user = userService.createOrUpdateUser(principal);
        return Map.of(
                "authenticated", true,
                "name", user.name(),
                "email", user.email(),
                "avatar", user.avatarPath());
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpServletRequest request, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            userService.deleteUserAvatar(principal.getAttribute("id"));
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return Map.of("message", "Wylogowano pomyślnie");
    }
}