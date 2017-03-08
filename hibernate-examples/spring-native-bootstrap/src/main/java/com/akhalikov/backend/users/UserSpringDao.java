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
  @Transactional(readOnly = true)
  public Optional<User> get(int userId) {
    return Optional.ofNullable(getSession().get(User.class, userId));
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAll() {
    return getSession().createQuery("FROM User").list();
  }

  @Override
  @Transactional
  public void insert(User user) {
    getSession().saveOrUpdate(user);
  }

  @Override
  @Transactional(readOnly = true)       // problem: postgres fails with error
  public void delete(int userId) {      // cannot execute DELETE in a read-only transaction

    getSession().createNativeQuery("delete from users where user_id = :id")
      .setParameter("id", userId)
      .executeUpdate();
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
