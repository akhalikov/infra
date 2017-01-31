package com.akhalikov;

import com.akhalikov.entity.Event;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public abstract class CacheTestBase extends TestBase {
  @Inject
  private EventDao eventDao;

  @Test
  public void testL2CacheHit() throws Exception {
    final int eventId = 1;

    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);

    //assertEquals(statistics.getSecondLevelCacheHitCount(), 1);

    //assertTrue(cache.containsEntity(Event.class, eventId));
  }

  @Test
  public void testL2CacheMiss() throws Exception {
    eventDao.getEvent(3);
    eventDao.getEvent(4);

    //assertEquals(statistics.getSecondLevelCacheHitCount(), 0);
  }
}
