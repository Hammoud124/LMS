package data.db.users;

import config.DBConfig;
import data.models.User;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final UserDAOImpl instance = new UserDAOImpl();


    public static UserDAO getInstance() {
        return instance;
    }


    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
    }

    private UserDAOImpl() {

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM users";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(User.fromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Proper logging should be used in production code
        }

        return users;
    }

    @Override
    public User getUserInfoById(int userID) {
        User user = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM users where UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return user = User.fromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean addNewUser(User user) {
        try (Connection conn = getConnection()) {
            String sql = "insert into users (Username,Password,Email,PhoneNumber,Address,Type) values (?,?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(sql);


            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassWord());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getAddress());
            stmt.setInt(6, user.getType().ordinal() + 1);


            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserInfoByEmailAndPassword(String email, String password) {
        User user = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM users where Email = ? and Password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return user = User.fromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public @Nullable User searchByEmail(String email) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
//            email = String.format("'%s'", email);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

           if(rs.next()){
              return User.fromResultSet(rs);
           }else {
               return null;
           }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
