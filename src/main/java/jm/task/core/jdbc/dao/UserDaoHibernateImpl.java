package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(SessionFactory factory = Util.buildSessionFactory(); Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastname VARCHAR(100), age TINYINT)").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {}
    }

    @Override
    public void dropUsersTable() {
        try(SessionFactory factory = Util.buildSessionFactory(); Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {}
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(SessionFactory factory = Util.buildSessionFactory(); Session session = factory.getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(SessionFactory factory = Util.buildSessionFactory(); Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (SessionFactory factory = Util.buildSessionFactory(); Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User")
                    .getResultList();
            session.getTransaction().commit();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(SessionFactory factory = Util.buildSessionFactory(); Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
