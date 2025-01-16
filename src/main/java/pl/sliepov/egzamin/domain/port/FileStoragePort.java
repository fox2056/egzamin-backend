package pl.sliepov.egzamin.domain.port;

import org.springframework.core.io.Resource;

public interface FileStoragePort {
    void saveAvatar(String userId, String avatarUrl, String accessToken);

    void deleteAvatar(String userId);

    Resource getAvatar(String userId);
}