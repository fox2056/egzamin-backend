package pl.sliepov.egzamin.infrastructure.adapter;

import org.springframework.stereotype.Component;
import pl.sliepov.egzamin.domain.port.UserAvatarPathBuilder;

@Component
public class WebUserAvatarPathBuilder implements UserAvatarPathBuilder {
    private static final String AVATAR_PATH_PREFIX = "/api/users/";
    private static final String AVATAR_PATH_SUFFIX = "/avatar";

    @Override
    public String buildPath(String userId) {
        return AVATAR_PATH_PREFIX + userId + AVATAR_PATH_SUFFIX;
    }
}