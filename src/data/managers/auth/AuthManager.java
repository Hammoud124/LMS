package data.managers.auth;

import data.models.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AuthManager {

    @Nullable
    User getLoggedInUser();

    @Nullable
    User login(@NotNull String email, @NotNull String password);

    void logout();

    void addUserAuthListener(@NotNull UserAuthListener listener);

    void removeUserAuthListener(@NotNull UserAuthListener listener);
}
