package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Name1", "LastName1", (byte) 20);
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        userDao.saveUser("Name4", "LastName4", (byte) 38);

        userDao.removeUserById(1);
        List<User> list = userDao.getAllUsers();
        for (User el: list) {
            System.out.println(el);
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

//        UserDaoJDBCImpl user = new UserDaoJDBCImpl();
//        user.saveUser("wef", "wefw", (byte) 27);
//        user.cleanUsersTable();
//        ArrayList<User> list = (ArrayList<User>) user.getAllUsers();
//        for (User el: list) {
//            System.out.println(el);
//        }
    }
}
