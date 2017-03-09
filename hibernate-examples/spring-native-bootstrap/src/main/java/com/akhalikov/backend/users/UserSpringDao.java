package com.akhalikov.backend.users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserSpringDao {
  private final SessionFactory sessionFactory;

  public UserSpringDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true)
  public Optional<User> get(int userId) {
    return Optional.ofNullable(getSession().get(User.class, userId));
  }

  @Transactional(readOnly = true)
  public List<User> getAll() {
    return getSession().createQuery("FROM User").list();
  }

  @Transactional
  public void insert(User user) {
    getSession().saveOrUpdate(user);
  }

  // problem: postgres fails with error
  // cannot execute DELETE in a read-only transaction
  @Transactional(readOnly = true)
  public void insertWithReadonlyTransaction(User user) {
    getSession().saveOrUpdate(user);
  }

  @Transactional
  public void delete(int userId) {

    getSession().createNativeQuery("delete from users where user_id = :id")
      .setParameter("id", userId)
      .executeUpdate();
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
