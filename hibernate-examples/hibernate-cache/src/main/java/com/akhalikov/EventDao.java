package com.akhalikov;

import com.akhalikov.entity.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

public class EventDao {
  private SessionFactory sessionFactory;

  EventDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  List<Event> getEvents() {
    try (Session session = sessionFactory.openSession()) {
      return session
        .createNativeQuery("SELECT * FROM event", Event.class)
        .getResultList();
    }
  }

  Event getEvent(int eventId) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(Event.class, eventId);
    }
  }

  void saveEvent(Event event) {
    try (Session session = sessionFactory.openSession()) {
      session.saveOrUpdate(event);
    }
  }
}
