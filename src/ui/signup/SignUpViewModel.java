package ui.signup;

import data.managers.signup.SignUpManger;
import data.managers.signup.SignUpMangerImpl;
import util.TextUtil;

import java.beans.PropertyChangeSupport;

public class SignUpViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private SignUpManger signUpManger;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;

    SignUpViewModel() {
        signUpManger = SignUpMangerImpl.getInstance();
    }

    public void observeUiState(SignUpUiStateListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void setUsername(String username) {
        this.username = username;
        validate();
    }

    public void setAddress(String address) {
        this.address = address;
        validate();
    }

    public void setPhone(String phone) {
        this.phone = phone;
        validate();
    }

    public void setEmail(String email) {
        this.email = email;
        validate();
    }

    public void setPassword(String password) {
        this.password = password;
        validate();
    }

    public void validate() {
        boolean isValid = !TextUtil.isEmpty(username) && !TextUtil.isEmpty(password) && !TextUtil.isEmpty(email) && !TextUtil.isEmpty(phone) && !TextUtil.isEmpty(address);
        support.firePropertyChange(SignUpButtonState.STATE_NAME, null, new SignUpButtonState(isValid));
    }

    public boolean signUp(String username, String password, String email, String phone, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;

        if (!signUpManger.signUp(username, password, email, phone, address)) {
            support.firePropertyChange(EmailState.STATE_NAME, null, new EmailState(true));
            return false;
        }
        return true;
    }
}
