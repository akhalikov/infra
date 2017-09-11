package akhalikov.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class UserDao {
  private final SessionFactory sessionFactory;

  public UserDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public User get(int userId) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(User.class, userId);
    }
  }

  public List<User> getUsers(List<Integer> userids) {
    try (Session session = sessionFactory.openSession()) {
      return session.byMultipleIds(User.class).multiLoad(userids);
    }
  }

  public void saveUser(User user) {
    try (Session session = sessionFactory.openSession()) {
      Transaction tx = session.beginTransaction();
      try {
        session.save(user);
        tx.commit();
      } catch (Exception e) {
        tx.rollback();
        throw e;
      }
    }
  }

  public void updateUser(User user) {
    try (Session session = sessionFactory.openSession()) {
      Transaction tx = session.beginTransaction();
      try {
        user.setUpdatedTime(LocalDateTime.now());
        session.update(user);
        tx.commit();
      } catch (Exception e) {
        tx.rollback();
        throw e;
      }
    }
  }

  public boolean deleteUser(int userId) {
    try (Session session = sessionFactory.openSession()) {
      User user = session.byId(User.class).load(userId);
      if (user == null) {
        return false; // no such user exists
      }
      Transaction tx = session.beginTransaction();
      try {
        session.delete(user);
        tx.commit();
        return true;
      } catch (Exception e) {
        tx.rollback();
        throw e;
      }
    }
  }
}
