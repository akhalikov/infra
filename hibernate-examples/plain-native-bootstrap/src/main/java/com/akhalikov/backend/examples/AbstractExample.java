package com.akhalikov.backend.examples;

import com.akhalikov.backend.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

abstract class AbstractExample {
  static {
    ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
  }

  final SessionFactory sessionFactory;

  AbstractExample() {
    sessionFactory = HibernateUtils.createSessionFactory();
  }

  void go() {
    try {
      addTestData();
      play();
    } catch (Exception e) {
      throw new RuntimeException("Failed to run example", e);
    } finally {
      clean();
      sessionFactory.close();
    }
  }

  abstract void play() throws Exception;

  private void addTestData() {
    System.out.println("add test data...");

    try (Session session = sessionFactory.openSession()) {
      session.getTransaction().begin();
      session.createNativeQuery("insert into users values (1,'John','Doe')").executeUpdate();
      session.createNativeQuery("insert into users values (2,'Helen','Monro')").executeUpdate();
      session.createNativeQuery("insert into users values (3,'Ivan','Dorn')").executeUpdate();
      session.getTransaction().commit();
    }
  }

  private void clean() {
    System.out.println("clean up...");

    try (Session session = sessionFactory.openSession()) {
      session.getTransaction().begin();
      session.createNativeQuery("delete from users").executeUpdate();
      session.getTransaction().commit();
    }
  }
}
