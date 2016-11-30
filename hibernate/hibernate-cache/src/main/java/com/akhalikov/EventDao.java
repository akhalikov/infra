package com.akhalikov;

import com.akhalikov.entity.Event;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class EventDao {
  private final SessionFactory sessionFactory;

  public EventDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @SuppressWarnings("unchecked")
  public List<Event> getEvents() {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Event.class);
    return criteria.list();
  }

  public Event getEvent(int eventId) {
    final Session session = sessionFactory.getCurrentSession();
    return (Event) session.get(Event.class, eventId);
  }

  public void saveEvent(Event event) {
    sessionFactory.getCurrentSession().saveOrUpdate(event);
  }
}
