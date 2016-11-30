package com.akhalikov;

import com.akhalikov.entity.Event;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class EventDao {
  private final SessionFactory sessionFactory;

  public EventDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @SuppressWarnings("unchecked")
  List<Event> getEvents() {
    final Session session = sessionFactory.openSession();
    Criteria criteria = session.createCriteria(Event.class);
    return criteria.list();
  }

  Event getEvent(int eventId) {
    final Session session = sessionFactory.openSession();
    Event event = (Event) session.get(Event.class, eventId);
    session.close();
    return event;
  }

  Event getEventWithCriteria(int eventId) {
    final Session session = sessionFactory.openSession();
    Criteria criteria = session.createCriteria(Event.class)
        .add(Restrictions.naturalId().set("id", eventId));
    Event event = (Event) criteria.uniqueResult();
    session.close();
    return event;
  }
}
