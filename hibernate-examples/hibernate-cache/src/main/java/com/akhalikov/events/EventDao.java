package com.akhalikov.events;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
  private SessionFactory sessionFactory;

  public EventDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<Event> getEvents() {
    try (Session session = sessionFactory.openSession()) {
      return session
        .createNativeQuery("SELECT * FROM event", Event.class)
        .getResultList();
    }
  }

  public List<Event> getEvents(Serializable... ids) {
    try (Session session = sessionFactory.openSession()) {
      List<Event> events = new ArrayList<>();
      for (Serializable id: ids) {
        events.add(session.get(Event.class, id));
      }
      return events;
    }
  }

  public Event getEvent(int eventId) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(Event.class, eventId);
    }
  }

  public void saveEvent(Event event) {
    try (Session session = sessionFactory.openSession()) {
      session.saveOrUpdate(event);
    }
  }
}
