package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }
    Util util;
    {
        try {
            util = new Util();
        } catch (SQLException ignored) {
        }
    }
    Connection connection = Objects.requireNonNull(util).getDataConnection();

    @Override
    public void createUsersTable() {
        try {
            String SQL = "CREATE TABLE users ( id BIGINT UNSIGNED AUTO_INCREMENT  PRIMARY KEY , name TEXT NOT NULL, lastName TEXT NOT NULL , age TINYINT NOT NULL)";
            Statement statement = connection.createStatement();
            statement.execute(SQL);
        } catch (SQLException ignored) {
        }

    }

    @Override
    public void dropUsersTable() {
        try {
            String SQL = "DROP TABLE users";
            Statement statement = connection.createStatement();
            statement.execute(SQL);
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            String SQL = "insert into users (name, lastName, age) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString (1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        try {
            String SQL = "DELETE users FROM users WHERE id = ?";  //UPDATE  users SET id = id - 1
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet =  preparedStatement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            String SQL = "TRUNCATE TABLE users";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.execute(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
