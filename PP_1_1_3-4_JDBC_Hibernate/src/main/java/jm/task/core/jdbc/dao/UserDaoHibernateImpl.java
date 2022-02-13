package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE users ( id BIGINT UNSIGNED AUTO_INCREMENT  PRIMARY KEY , name TEXT NOT NULL, lastName TEXT NOT NULL , age TINYINT NOT NULL)")
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception ignored) {
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE users")
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }catch (Exception ignored) {
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()){
            User user = session.load(User.class, id);
            session.delete(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()){
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("TRUNCATE TABLE users")
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }catch (Exception ignored) {
        }
    }
}
