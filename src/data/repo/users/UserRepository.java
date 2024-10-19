package data.repo.users;

import data.models.User;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    User getUserInfoById(int id);

    boolean addNewUser(User user);

    User getUserInfoByEmailAndPassword(String email, String password);

    /**
     * Searches users by email.
     *
     * @param email The email to search for.
     * @return {@link User} if exists or null if it doesn't.
     */
    @Nullable
    User searchByEmail(String email);
}
