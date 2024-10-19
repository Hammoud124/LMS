package data.models;

import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private int id;
    private String name;
    private String passWord;
    private String email;
    private String address;
    private String phoneNumber;
    private Type type;

    public User(int id, String name, String passWord, String email, String address, String phoneNumber, Type type) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.passWord = passWord;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public User() {
    }

    public enum Type {
        USER,
        ADMIN
    }

    @Nullable
    public static User fromResultSet(ResultSet result) throws SQLException {
        return new User()
                .setId(result.getInt("UserID"))
                .setEmail(result.getString("email"))
                .setName(result.getString("username"))
                .setPassWord(result.getString("password"))
                .setAddress(result.getString("Address"))
                .setPhoneNumber(result.getString("phoneNumber"))
                .setType(Type.valueOf(result.getString("Type")));


    }

    public static User newUser(String name, String passWord, String email, String address, String phoneNumber) {
        return new User()
                .setName(name)
                .setPassWord(passWord)
                .setEmail(email)
                .setAddress(address)
                .setPhoneNumber(phoneNumber)
                .setType(Type.USER);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Type getType() {
        return type;
    }

    public User setType(Type type) {
        this.type = type;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public User setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isAdmin() {
        return type == Type.ADMIN;
    }
}
