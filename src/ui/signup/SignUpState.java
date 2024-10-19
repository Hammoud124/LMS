package ui.signup;

public sealed interface SignUpState permits SignUpButtonState,EmailState {


}
record SignUpButtonState(boolean isEnabled) implements SignUpState {
    static final String STATE_NAME = SignUpButtonState.class.getSimpleName();
}

record EmailState(boolean exists) implements SignUpState {
    static final String STATE_NAME = EmailState.class.getSimpleName();
}
