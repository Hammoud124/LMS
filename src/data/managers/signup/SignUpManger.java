package data.managers.signup;

import data.models.User;

public interface SignUpManger {


    boolean signUp(String username, String password, String email, String phone, String address);
}
