package pl.sliepov.egzamin.application.usecase.user;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import pl.sliepov.egzamin.domain.model.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sliepov.egzamin.domain.port.FileStoragePort;
import pl.sliepov.egzamin.domain.port.UserAvatarPathBuilder;

@Service
public class UserService {
    private final FileStoragePort fileStorage;
    private final UserAvatarPathBuilder pathBuilder;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public UserService(
            FileStoragePort fileStorage,
            UserAvatarPathBuilder pathBuilder,
            OAuth2AuthorizedClientService authorizedClientService) {
        this.fileStorage = fileStorage;
        this.pathBuilder = pathBuilder;
        this.authorizedClientService = authorizedClientService;
    }

    public User createOrUpdateUser(OAuth2User oAuth2User) {
        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        String userId = oAuth2User.getAttribute("id");
        String accessToken = client.getAccessToken().getTokenValue();

        String avatarUrl = "https://graph.facebook.com/v21.0/" + userId + "/picture?type=large";
        String avatarPath = pathBuilder.buildPath(userId);
        fileStorage.saveAvatar(userId, avatarUrl, accessToken);

        return new User(
                userId,
                oAuth2User.getAttribute("name"),
                oAuth2User.getAttribute("email"),
                avatarPath);
    }

    public void deleteUserAvatar(String userId) {
        fileStorage.deleteAvatar(userId);
    }
}