package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private final Connection connection = Util.getInstance().getConnection();
    public void createUsersTable() {
        try {
            try (Statement statement = connection.createStatement()){
                statement.execute("CREATE TABLE IF NOT EXISTS Users ("+
                        " id INT NOT NULL AUTO_INCREMENT,"+
                        " name VARCHAR(50)," +
                        " lastName VARCHAR(50)," +
                        " age INT," +
                        "PRIMARY KEY(id));");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while creating the table" + e.getMessage());
        }
        System.out.println("DB created successful by JDBC");
    }

    public void dropUsersTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS Users");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while drop the user from the table" + e.getMessage());
        }
            System.out.println("Dropped users successful by JDBC");
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Users (name,lastName,age) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                int sou = preparedStatement.executeUpdate();
                if (sou > 0) {
                    System.out.println("User c именем - " + name + " добавлен в базу данных.");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while save user in the table" + e.getMessage());
        }
        System.out.println("User successful added by JDBC");
    }

    public void removeUserById(long id) {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE id = ?")){
                preparedStatement.setLong(1,id);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while removed user from the table" + e.getMessage());
        }
        System.out.println("User successful removed by JDBC");
    }

    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        try {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM Users")){
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");
                    Byte age = resultSet.getByte("age");

                    User user = new User(name, lastName, age);
                    user.setId(id);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while getting user from the table" + e.getMessage());
        }
        System.out.println("Users successful getting by JDBC");
        return users;
    }

    public void cleanUsersTable() {
        try {
            try (Statement statement = connection.createStatement();){
                statement.execute("DELETE FROM Users");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while clean the table" + e.getMessage());
        }
        System.out.println("DB clean successful by JDBC");
    }
}