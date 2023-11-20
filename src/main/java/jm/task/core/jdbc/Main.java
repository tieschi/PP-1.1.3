package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl user = new UserServiceImpl();

        user.dropUsersTable();
        user.dropUsersTable();
        user.createUsersTable();
        user.saveUser("John", "Smith", (byte) 45);
        user.saveUser("Ben", "White", (byte) 34);
        user.saveUser("Mike", "Ross", (byte) 12);
        user.saveUser("Luke", "Osborn", (byte) 83);
        System.out.println("-----------------------------------------------------------------");
        ArrayList<User> list = (ArrayList<User>) user.getAllUsers();
        for (User person: list) {
            System.out.println(person);
        }
        System.out.println("-----------------------------------------------------------------");
        user.removeUserById(2);
        ArrayList<User> list2 = (ArrayList<User>) user.getAllUsers();
        for (User person: list2) {
            System.out.println(person);
        }
        System.out.println("-----------------------------------------------------------------");
        user.cleanUsersTable();
        ArrayList<User> list1 = (ArrayList<User>) user.getAllUsers();
        for (User person: list1) {
            System.out.println(person);
        }
        user.dropUsersTable();
    }
}
