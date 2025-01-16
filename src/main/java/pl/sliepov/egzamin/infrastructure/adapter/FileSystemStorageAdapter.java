package pl.sliepov.egzamin.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pl.sliepov.egzamin.domain.port.FileStoragePort;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class FileSystemStorageAdapter implements FileStoragePort {

    @Value("${app.avatar.directory}")
    private String avatarDirectory;

    @Override
    public void saveAvatar(String userId, String avatarUrl, String accessToken) {
        String fileName = userId + ".jpg";
        String filePath = avatarDirectory + "/" + fileName;

        try {
            URL url = URI.create(avatarUrl + "&access_token=" + accessToken).toURL();
            Path targetPath = Path.of(filePath);
            Files.createDirectories(targetPath.getParent());
            try (InputStream in = url.openStream()) {
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się pobrać avatara", e);
        }
    }

    @Override
    public Resource getAvatar(String userId) {
        Path avatarPath = Path.of(avatarDirectory, userId + ".jpg");
        return new FileSystemResource(avatarPath);
    }

    @Override
    public void deleteAvatar(String userId) {
        try {
            Files.deleteIfExists(Path.of(avatarDirectory, userId + ".jpg"));
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się usunąć avatara", e);
        }
    }
}