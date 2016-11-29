package com.akhalikov;

import org.hibernate.SessionFactory;

import java.util.List;

public class App {
  private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  public static void main(String[] args) {
    final EventDao eventDao = new EventDao(sessionFactory);

    eventDao.addEvent("Event A");
    eventDao.addEvent("Event B");

    List<Event> events = eventDao.getEvents();
    events.forEach(e -> System.out.println(e.toString()));

    HibernateUtil.shutdown();
  }
}
