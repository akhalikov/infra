package com.akhalikov.backend.users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class UserHibernateDao implements UserDao {

  private final SessionFactory sessionFactory;

  public UserHibernateDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Optional<User> get(int userId) {
    return null;
  }

  @Override
  public List<User> getAll() {
    try (Session session = sessionFactory.openSession()) {
      return session.createQuery("SELECT id FROM User").list();
    }
  }

  @Override
  public void insert(User user) {

  }

  @Override
  public void delete(int userId) {

  }
}
