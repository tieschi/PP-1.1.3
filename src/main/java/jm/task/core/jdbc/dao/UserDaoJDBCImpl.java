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

    Connection connection;
    PreparedStatement preparedStatement;

    private static final String CREATE_TABLE = "CREATE TABLE `user` (`id` INT NOT NULL AUTO_INCREMENT, " +
            "`name` VARCHAR(45) NULL, `lastName` VARCHAR(45) NULL, `age` INT NULL, PRIMARY KEY (`id`));";
    private static final String DROP_TABLE = "DROP TABLE `user`";
    private static final String ADD_USER = "INSERT INTO `user` (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM `user` WHERE id=?";
    private static final String GET_ALL_USER = "SELECT * FROM `user`";
    private static final String DELETE_ALL = "DELETE FROM `user`";

    public UserDaoJDBCImpl() {
        Util util = new Util();
        this.connection = util.getConnection();
    }

    public void createUsersTable() {
        try {
            preparedStatement = connection.prepareStatement(CREATE_TABLE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("table user has already be created");
        }

    }

    public void dropUsersTable() {
        try {
            preparedStatement = connection.prepareStatement(DROP_TABLE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("table user not found");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("some errors with user added");
        }
    }

    public void removeUserById(long id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("some errors with user deleted");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_USER);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name =  resultSet.getString("name");
                String lastName =  resultSet.getString("lastName");
                Byte age =  resultSet.getByte("age");
                list.add(new User(name, lastName, age));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("some errors with users selected");;
            return null;
        }
    }

    public void cleanUsersTable() {
        try {
            preparedStatement = connection.prepareStatement(DELETE_ALL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("some errors with users deleted");
        }
    }
}
