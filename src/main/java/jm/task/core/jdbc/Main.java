package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Viktoria","Malienko",(byte) 38);
        userService.saveUser("Nikita","Ivanov",(byte) 12);
        userService.saveUser("Daria","Kolesnikova",(byte) 23);
        userService.saveUser("Ruslana","Konstantinova",(byte) 18);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
