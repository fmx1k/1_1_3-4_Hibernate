package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static String CREATE_TABLE = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastname VARCHAR(100), age TINYINT)";
    private static String INSERT_USER = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?);";
    private static String DELETE_USER = "DELETE FROM users WHERE id = ?";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); PreparedStatement ps = connection.prepareStatement(CREATE_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); PreparedStatement ps = connection.prepareStatement("DROP TABLE users")) {
            ps.execute();
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection(); PreparedStatement ps = connection.prepareStatement(DELETE_USER)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = Util.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastname, age);
                user.setId(id);
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE FROM users")) {

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
