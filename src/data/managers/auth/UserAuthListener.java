package data.managers.auth;

import data.models.User;

public interface UserAuthListener {

    void onUserAuthorized(User user);

    void onUserUnauthorized();
}
