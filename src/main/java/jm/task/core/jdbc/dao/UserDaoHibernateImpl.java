package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age SMALLINT)").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.err.println("Что-то пошло не так при создании таблицы: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE users").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        }catch (Exception e) {
            System.err.println("Что-то пошло не так при удалении табоицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch (Exception e) {
            System.err.println("Что-то пошло не так при сохранении пользователя: " + e.getMessage());
        }
        System.out.println("User с именем " + name + " добавлен в таблицу");
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e) {
            System.err.println("Что-то пошло не так при удалении пользователя по id : " + e.getMessage());
        }
        System.out.println("User c id = " + id + "успешно удален");
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
            try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.createNativeQuery("delete from users").executeUpdate();
            session.getTransaction().commit();
                System.out.println("Таблица очищена ");
            } catch (Exception e) {
                System.err.println("Ошибка при очистке таблицы");
            }
    }
}


