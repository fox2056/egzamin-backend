package pl.sliepov.egzamin.infrastructure.web.user;

import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import pl.sliepov.egzamin.domain.port.FileStoragePort;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "${app.frontend.url}", allowCredentials = "true")
public class UserController {
    private final FileStoragePort fileStorage;

    public UserController(FileStoragePort fileStorage) {
        this.fileStorage = fileStorage;
    }

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<Resource> getAvatar(
            @PathVariable String userId,
            @AuthenticationPrincipal OAuth2User principal) {

        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            Resource resource = fileStorage.getAvatar(userId);
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                        .header(HttpHeaders.CACHE_CONTROL, "private") // Zapobiega cachowaniu przez proxy
                        .header(HttpHeaders.PRAGMA, "no-cache")
                        .body(resource);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}