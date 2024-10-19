package data.db.users;

import data.models.User;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();

    User getUserInfoById(int id);

    boolean addNewUser(User user);

    User getUserInfoByEmailAndPassword(String email, String password);

    @Nullable
    User searchByEmail(String email);
}
