package ui.signup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

public class SignUpUiStateListener implements PropertyChangeListener {

    private final Consumer<SignUpState> onSignUpStateChanged;

    public SignUpUiStateListener(Consumer<SignUpState> onChanged) {
        this.onSignUpStateChanged = onChanged;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals(SignUpButtonState.STATE_NAME)) {
            SignUpButtonState state = (SignUpButtonState) evt.getNewValue();
            onSignUpStateChanged.accept(state);
        } else if (propertyName.equals(EmailState.STATE_NAME)) {
            EmailState state = (EmailState) evt.getNewValue();
            onSignUpStateChanged.accept(state);
        }
    }
}
