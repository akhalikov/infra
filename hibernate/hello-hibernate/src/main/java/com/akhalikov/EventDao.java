package com.akhalikov;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

class EventDao {
  private final SessionFactory sessionFactory;

  EventDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  List<Event> getEvents() {
    try (final Session session = sessionFactory.openSession()) {
      return session.createQuery("from Event", Event.class).list();
    }
  }

  void addEvent(String title) {
    try (final Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(new Event(title, new Date()));
      session.getTransaction().commit();
    }
  }
}
