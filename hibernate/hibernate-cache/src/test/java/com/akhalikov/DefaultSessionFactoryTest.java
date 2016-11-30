package com.akhalikov;

import com.akhalikov.config.SimpleHibernateConfig;
import com.akhalikov.entity.Event;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@ContextConfiguration(classes = {SimpleHibernateConfig.class})
public class DefaultSessionFactoryTest extends TestBase {
  @Inject
  private EventDao eventDao;

  @Test
  public void shouldGetEvents() throws Exception {
    List<Event> events = eventDao.getEvents();
    assertEquals(events.size(), 4);
    assertEquals(events.get(0).getTitle(), "Java Meetup");
    assertEquals(events.get(1).getTitle(), "Python Meetup");
    assertEquals(events.get(2).getTitle(), "Go Meetup");
    assertEquals(events.get(3).getTitle(), "Hackathon");
  }

  @Test
  public void testThatL2CacheIsOff() throws Exception {
    final int eventId = 1;

    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);

    assertEquals(statistics.getSecondLevelCacheHitCount(), 0);

    assertFalse(cache.containsEntity(Event.class, eventId));
  }
}