package com.akhalikov;

import com.akhalikov.config.AppContextConfig;
import com.akhalikov.config.DefaultHibernateConfig;
import com.akhalikov.event.Event;
import com.akhalikov.event.EventDao;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = {AppContextConfig.class, DefaultHibernateConfig.class})
public class SessionFactoryTest extends SessionFactoryTestBase {
  @Inject
  private EventDao eventDao;

  @Test
  public void shouldGetEvents() throws Exception {
    List<Event> events = eventDao.getEvents();
    assertEquals(events.size(), 2);
    assertEquals(events.get(0).getTitle(), "Java Meetup");
    assertEquals(events.get(1).getTitle(), "Python Meetup");
  }

  @Test
  public void shouldNotUseCache() throws Exception {
    getEventFromSessionTwice();

    long l2CacheHits = statistics.getSecondLevelCacheHitCount();
    assertEquals(l2CacheHits, 0);
  }
}