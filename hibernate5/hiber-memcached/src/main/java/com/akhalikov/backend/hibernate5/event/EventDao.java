package com.akhalikov.backend.hibernate5.event;

import org.hibernate.SessionFactory;

import java.util.List;

public class EventDao {
  private final SessionFactory sessionFactory;

  public EventDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<Event> getEvents() {
    return sessionFactory.openSession()
        .createNativeQuery("SELECT * FROM event", Event.class)
        .getResultList();
  }
}
