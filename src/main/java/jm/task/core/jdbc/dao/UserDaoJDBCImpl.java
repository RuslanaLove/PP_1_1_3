package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Users (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50),
                lastName VARCHAR(50),
                age TINYINT)
                """;

        try (Connection connection = Util.getInstance().getConnection()){
            Statement statement = connection.createStatement();

            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql= "DROP TABLE IF EXISTS Users";

        try (Connection connection = Util.getInstance().getConnection()){
            Statement statement = connection.createStatement();

            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (name,lastName,age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            int sou = preparedStatement.executeUpdate();
            if (sou > 0) {
                System.out.println("User c именем - " + name + " добавлен в базу данных.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE id = ?";

        try (Connection connection = Util.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();

        String sql = "SELECT * FROM Users";

        try (Connection connection = Util.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {String sql = "DELETE FROM Users";

        try (Connection connection = Util.getInstance().getConnection()){
            Statement statement = connection.createStatement();

            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
