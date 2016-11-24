package com.akhalikov;

import com.akhalikov.entity.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EventDao {
  private final SessionFactory sessionFactory;

  public EventDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  List<Event> getEvents() {
    return sessionFactory.openSession()
        .createNativeQuery("SELECT * FROM event", Event.class)
        .getResultList();
  }

  Event getEvent(int eventId) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(Event.class, eventId);
    }
  }
}
