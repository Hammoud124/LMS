package data.repo.users;

import data.db.users.UserDAOImpl;
import data.models.User;

import java.util.List;
import data.db.users.UserDAO;
import org.jetbrains.annotations.Nullable;

public class UserRepositoryImpl implements UserRepository {

    public static final UserRepositoryImpl instance = new UserRepositoryImpl();

    private User loggedInUser;
    
    public static UserRepository getInstance() {
        return instance;
    }

    private final UserDAO userDAO;

    private UserRepositoryImpl() {
        userDAO = UserDAOImpl.getInstance();
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserInfoById(int id) {
        return userDAO.getUserInfoById(id);
    }

    @Override
    public boolean addNewUser(User user) {
       return userDAO.addNewUser(user);
    }

    @Override
    public User getUserInfoByEmailAndPassword(String email, String password) {
       return userDAO.getUserInfoByEmailAndPassword(email, password);
    }

    @Override
    public @Nullable User searchByEmail(String email) {
       return userDAO.searchByEmail(email);
    }
}
