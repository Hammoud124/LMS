package ui.login;

import data.managers.auth.AuthManager;
import data.managers.auth.AuthManagerImpl;
import data.models.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel {

    private final AuthManager authManager;

    private final PropertyChangeSupport nextScreenSupport = new PropertyChangeSupport(this);

    public LoginViewModel() {
        this.authManager = AuthManagerImpl.getInstance();
    }

    public void addOnNextScreenListener(final PropertyChangeListener listener) {
        nextScreenSupport.addPropertyChangeListener(listener);
    }

    public boolean login(String email, String password) {
        // Incorrect email or password.
        User user = authManager.login(email, password);
        if (user != null) {
            nextScreenSupport.firePropertyChange("nextScreen", null, user.isAdmin()
                    ? NextScreen.ADMIN_SCREEN
                    : NextScreen.USER_SCREEN);
            return true;
        }
        return false;
    }

    enum NextScreen {
        ADMIN_SCREEN, USER_SCREEN
    }
}