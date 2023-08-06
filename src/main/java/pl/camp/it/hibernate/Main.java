package pl.camp.it.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class Main {
    public static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        User user = new User(0, "zbyszek", "zbyszek", User.Role.ADMIN);
        User user2 = new User();
        user2.setId(1);
        //persistUser(user);
        //updateUser(user);
        //deleteUser(user2);
        //deleteUser2(0);
        //System.out.println(user);
        /*System.out.println(getAllUsers());
        System.out.println(getUserById(15));*/
        /*Optional<User> userFromDB = getUserById(1);
        System.out.println(userFromDB.get());*/
        /*Order order = new Order();
        order.setUser(userFromDB.get());
        order.setTotal(200.00);

        persistOrder(order);
        userFromDB.get().getOrders().add(order);
        updateUser(userFromDB.get());*/
        //System.out.println(getOrderById(1).get().getUser());

        /*Order o1 = new Order();
        o1.setTotal(100.00);

        Order o2 = new Order();
        o2.setTotal(150.00);

        Order o3 = new Order();
        o3.setTotal(180.00);

        Order o4 = new Order();
        o4.setTotal(200.00);

        Order o5 = new Order();
        o5.setTotal(250.00);

        user.getOrders().add(o1);
        user.getOrders().add(o2);
        user.getOrders().add(o3);
        user.getOrders().add(o4);
        user.getOrders().add(o5);

        persistUser(user);*/

        Optional<User> user3 = getUserById(1);
        user3.get().getOrders().forEach(o -> o.setTotal(o.getTotal() + 50));

        updateUser(user3.get());
    }

    public static void persistUser(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("loginy zle !!!");
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void updateUser(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void deleteUser(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void deleteUser2(int id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(new User(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery(
                "FROM pl.camp.it.hibernate.User", User.class);
        List<User> result = query.getResultList();
        session.close();
        return result;
    }

    public static Optional<User> getUserById(int id) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery(
                "FROM pl.camp.it.hibernate.User WHERE id = :id", User.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    public static void persistOrder(Order order) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static Optional<Order> getOrderById(int id) {
        Session session = sessionFactory.openSession();
        Query<Order> query = session.createQuery(
                "FROM pl.camp.it.hibernate.Order WHERE id = :id", Order.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }
}
