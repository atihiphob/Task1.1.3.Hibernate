package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl tablet = new UserDaoHibernateImpl();
        tablet.saveUser("Вася", "Пупкин", (byte) 2);
        tablet.saveUser("Вася", "Пупкин", (byte) 2);
        tablet.saveUser("Вася", "Пупкин", (byte) 2);
        tablet.saveUser("Вася", "Пупкин", (byte) 2);
        List <User> userList1 = tablet.getAllUsers();
        System.out.println(userList1);
        tablet.cleanUsersTable();
        tablet.dropUsersTable();

    }
}
