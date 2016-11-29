package com.akhalikov;

import com.akhalikov.config.AppContextConfig;
import com.akhalikov.config.MultiCacheHibernateConfig;
import com.akhalikov.multicache.CacheRegion;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.hibernate.stat.EntityStatistics;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@ContextConfiguration(classes = {AppContextConfig.class, MultiCacheHibernateConfig.class})
public class MultiCacheSessionFactoryTest extends TestBase {
  private static final String DOCUMENT_ENTITY_KEY = "com.akhalikov.entity.Document";
  private static final String EVENT_ENTITY_KEY = "com.akhalikov.entity.Event";

  @Inject
  private DocumentDao documentDao;

  @Inject
  private EventDao eventDao;

  @BeforeClass
  public void setUp() throws Exception {
    jdbcTemplate.execute("INSERT INTO document (title, author) VALUES ('Java In Action', 'Monty Python')");
    jdbcTemplate.execute("INSERT INTO document (title, author) VALUES ('Learning Python', 'John Doe')");
    jdbcTemplate.execute("INSERT INTO document (title, author) VALUES ('Meaning Of Life And Everything', '42')");
    jdbcTemplate.execute("INSERT INTO document (title, author) VALUES ('42', 'Green Hole')");
  }

  @Test
  public void shouldUseEhCacheForLocalRegion() throws Exception {
    final int documentId = 1;

    documentDao.getDocument(documentId);
    documentDao.getDocument(documentId);

    CacheManager cacheManager = CacheManager.create();
    Ehcache ehcache = cacheManager.getEhcache(CacheRegion.LOCAL);
    assertNotNull(ehcache);

    assertEquals(ehcache.getSize(), 1);

    List keys = ehcache.getKeys();
    Object key = keys.get(0);
    assertEquals(key.toString(), DOCUMENT_ENTITY_KEY + "#" + documentId);

    assertEquals(statistics.getSecondLevelCacheHitCount(), 1);
    EntityStatistics entityStatistics = statistics.getEntityStatistics(DOCUMENT_ENTITY_KEY);
    System.out.println(entityStatistics);
  }

  public void shouldUseMemcachedByDefault() {
    final int eventId = 1;

    eventDao.getEvent(eventId);
    eventDao.getEvent(eventId);

    assertEquals(statistics.getSecondLevelCacheHitCount(), 1);
  }
}
