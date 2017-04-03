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
      clean();
      addTestData();
      play();
    } catch (Exception e) {
      throw new RuntimeException("Failed to run example", e);
    } finally {
      sessionFactory.close();
    }
  }

  abstract void play() throws Exception;

  private void addTestData() throws Exception {
    try (Session session = sessionFactory.openSession()) {
      System.out.println("create schema...");
      session.getTransaction().begin();
      session.getTransaction().commit();

      System.out.println("add test data...");
      session.getTransaction().begin();
      session.createNativeQuery("INSERT INTO users (last_name, first_name) VALUES ('John','Doe')").executeUpdate();
      session.createNativeQuery("INSERT INTO users (last_name, first_name) VALUES ('Helen','Monro')").executeUpdate();
      session.createNativeQuery("INSERT INTO users (last_name, first_name) VALUES ('Ivan','Dorn')").executeUpdate();
      session.getTransaction().commit();
    }
  }

  private void clean() {
    System.out.println("clean up...");

    try (Session session = sessionFactory.openSession()) {
      session.getTransaction().begin();
      session.createNativeQuery("DELETE FROM users").executeUpdate();
      session.createNativeQuery("ALTER SEQUENCE users_user_id_seq RESTART WITH 1;").executeUpdate();
      session.getTransaction().commit();
    }
  }
}
