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
    private static final String DELETE_ALL = "TRUNCATE `user`";
    private static final String CHECK_TABLE = "SHOW TABLES LIKE 'user'";

    public UserDaoJDBCImpl() {
        Util util = new Util();
        this.connection = util.getConnection();
    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE);
             PreparedStatement preparedStatement1 = connection.prepareStatement(CHECK_TABLE)) {
            ResultSet resultSet = preparedStatement1.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count ++;
            }
            if (count == 0) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE);
             PreparedStatement preparedStatement1 = connection.prepareStatement(CHECK_TABLE)){
            ResultSet resultSet = preparedStatement1.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count ++;
            }
            if (count > 0) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name =  resultSet.getString("name");
                String lastName =  resultSet.getString("lastName");
                Byte age =  resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId((long) id);
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
