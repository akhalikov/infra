package com.akhalikov.tutorial.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

public class App {

  private final SessionFactory sessionFactory;

  private App() {
    sessionFactory = HibernateUtil.getSessionFactory();
  }

  private void start() {
    createEvents();
    pullEvents();
  }

  private void createEvents() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(new Event("Event A", new Date()));
    session.save(new Event("Event B", new Date()));
    session.getTransaction().commit();
    session.close();
  }

  @SuppressWarnings("unchecked")
  private void pullEvents() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<Event> events = session.createQuery("from Event").list();
    events.forEach(e -> System.out.println(e.toString()));
    session.getTransaction().commit();
    session.close();
  }

  public static void main(String[] args) {
    new App().start();
  }
}
