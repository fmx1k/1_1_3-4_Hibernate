package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Александр", "Евгеньевич", (byte) 25);
        userService.saveUser("Марина", "Олеговна", (byte) 35);
        userService.saveUser("Алексей", "Павлович", (byte) 45);
        userService.saveUser("Сергей", "Иванович", (byte) 55);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();



    }
}
