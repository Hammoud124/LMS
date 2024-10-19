package data.managers.auth;

import data.models.User;
import data.repo.users.UserRepository;
import data.repo.users.UserRepositoryImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class AuthManagerImpl implements AuthManager {

    private static final AuthManager instance = new AuthManagerImpl();
    private final UserRepository userRepository;

    private final Set<UserAuthListener> userAuthListeners = new HashSet<>();

    private User user;

    public static AuthManager getInstance() {
        return instance;
    }

    private AuthManagerImpl() {
        this.userRepository = UserRepositoryImpl.getInstance();
    }

    @Override
    public @Nullable User getLoggedInUser() {
        return user;
    }

    @Override
    public @Nullable User login(@NotNull String email, @NotNull String password) {
        user = userRepository.getUserInfoByEmailAndPassword(email, password);
        if (user != null) {
            // notify user is now logged in
            notifyUserAuthorized(user);
        }
        return user;
    }

    @Override
    public void logout() {
        notifyUserUnAuthorized();
    }

    @Override
    public void addUserAuthListener(@NotNull UserAuthListener listener) {
        userAuthListeners.add(listener);
    }

    @Override
    public void removeUserAuthListener(@NotNull UserAuthListener listener) {
        userAuthListeners.remove(listener);
    }

    private void notifyUserAuthorized(@NotNull User user) {
        userAuthListeners.forEach(l -> l.onUserAuthorized(user));
    }

    private void notifyUserUnAuthorized() {
        userAuthListeners.forEach(UserAuthListener::onUserUnauthorized);
    }
}
