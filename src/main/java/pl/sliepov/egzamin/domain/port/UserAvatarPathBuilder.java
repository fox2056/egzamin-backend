package pl.sliepov.egzamin.domain.port;

public interface UserAvatarPathBuilder {
    String buildPath(String userId);
}