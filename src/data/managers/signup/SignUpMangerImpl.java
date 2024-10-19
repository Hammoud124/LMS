package data.managers.signup;

import data.models.User;
import data.repo.users.UserRepository;
import data.repo.users.UserRepositoryImpl;

public class SignUpMangerImpl implements SignUpManger {
    private static final SignUpMangerImpl instance = new SignUpMangerImpl();
    private final UserRepository userRepository;

    private SignUpMangerImpl() {
        userRepository = UserRepositoryImpl.getInstance();
    }

    public static SignUpManger getInstance() {
        return instance;
    }

    @Override
    public boolean signUp(String username, String password, String email, String phone, String address) {
        if (userRepository.searchByEmail(email) != null) {
            return false;
        }
        User user = User.newUser(username, password, email, phone, address);
        return userRepository.addNewUser(user);
    }
}