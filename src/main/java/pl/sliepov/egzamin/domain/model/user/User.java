package pl.sliepov.egzamin.domain.model.user;

public record User(
        String id,
        String name,
        String email,
        String avatarPath) {
}