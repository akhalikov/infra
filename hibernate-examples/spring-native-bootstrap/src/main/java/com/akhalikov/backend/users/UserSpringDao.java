package com.akhalikov.backend.users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserSpringDao implements UserDao {
  private final SessionFactory sessionFactory;

  public UserSpringDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Optional<User> get(int userId) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAll() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("FROM User").list();
  }

  @Override
  public void insert(User user) {

  }

  @Override
  public void delete(int userId) {

  }
}
