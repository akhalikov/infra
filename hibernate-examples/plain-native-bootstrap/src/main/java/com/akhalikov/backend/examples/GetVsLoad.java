package com.akhalikov.backend.examples;

import com.akhalikov.backend.users.User;
import com.akhalikov.backend.utils.HibernateUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.proxy.LazyInitializer;

public class GetVsLoad extends AbstractExample {
  @Override
  void play() throws Exception {
    playLoad();

    playGet();
  }

  private void playLoad() {
    try (Session session = sessionFactory.openSession()) {

      // using session.load() to get proxy entity
      // in this case hibernate does not hit database
      User user = session.load(User.class, 1);

      // will output something like `User_$$_jvst9c1_0`
      System.out.println("proxy class=" + user.getClass().getSimpleName());

      LazyInitializer handler = HibernateUtils.getEntityProxy(user);

      // let's see what's inside
      System.out.println("entity name=" + handler.getEntityName());
      System.out.println("identifier=" + handler.getIdentifier());
      System.out.println("uninitialized=" + handler.isUninitialized()); // true - not initialized

      // now let's initialize our proxy
      // this time hibernate will hit database and initialize proxy with data
      Hibernate.initialize(user);

      System.out.println("uninitialized=" + handler.isUninitialized()); // not false now

      System.out.println("user=" + user);
    }
  }

  private void playGet() {
    try (Session session = sessionFactory.openSession()) {

      // session.get() returns User object (not a proxy) and hits database
      User user = session.get(User.class, 1);

      System.out.println("user=" + user);
    }
  }

  public static void main(String[] args) {
    new GetVsLoad().go();
  }
}
