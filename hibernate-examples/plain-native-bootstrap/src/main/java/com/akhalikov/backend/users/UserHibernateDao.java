package com.akhalikov.backend.users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.time.LocalDateTime;
import java.util.List;

public class UserHibernateDao implements UserDao {

  private final SessionFactory sessionFactory;

  public UserHibernateDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public User get(int userId) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(User.class, userId);
    }
  }

  @Override
  public List<User> getAll() {
    try (Session session = sessionFactory.openSession()) {
      return session.createQuery("FROM User").list();
    }
  }

  @Override
  public void insert(User user) {
    try (Session session = sessionFactory.openSession()) {
      try {
        session.getTransaction().begin();
        session.saveOrUpdate(user);
        session.getTransaction().commit();

      } catch (Exception e) {

        if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
            || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {

          System.out.println("rolling back transaction");
          session.getTransaction().rollback();
        }

        throw new RuntimeException("failed to persist user " + user, e);
      }
    }
  }

  public void create(String firstName, String lastName) {
    insert(new User(firstName, lastName));
  }

  public void update(User user) {
    try (Session session = sessionFactory.openSession()) {
      try {
        session.getTransaction().begin();
        user.setUpdatedDateTime(LocalDateTime.now());
        session.saveOrUpdate(user);
        session.getTransaction().commit();

      } catch (Exception e) {

        if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
          || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {

          System.out.println("rolling back transaction");
          session.getTransaction().rollback();
        }

        throw new RuntimeException("failed to persist user " + user, e);
      }
    }
  }

  @Override
  public void delete(int userId) {
    try (Session session = sessionFactory.openSession()) {

      try {
        session.getTransaction().begin();

        session.createQuery("DELETE FROM User WHERE id = :id")
            .setParameter("id", userId)
            .executeUpdate();

        session.getTransaction().commit();

      } catch (Exception e) {
        if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
            || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {

          System.out.println("rolling back transaction");
          session.getTransaction().rollback();
        }

        throw new RuntimeException("failed to delete user by user_id=" + userId, e);
      }
    }
  }
}
