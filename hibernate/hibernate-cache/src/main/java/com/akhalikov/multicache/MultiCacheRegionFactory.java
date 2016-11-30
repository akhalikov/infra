package com.akhalikov.multicache;

import com.mc.hibernate.memcached.MemcachedRegionFactory;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.Settings;

import java.util.Properties;
import java.util.regex.Pattern;

public class MultiCacheRegionFactory implements RegionFactory {
  private static final String CONFIG_EHCACHE_REGION_FILTER = "hibernate.ehcache.region_filter";

  private MemcachedRegionFactory memcachedRegionFactory;
  private EhCacheRegionFactory ehCacheRegionFactory;
  private Pattern ehCacheFilterPattern;

  @Override
  public void start(Settings settings, Properties properties) throws CacheException {
    memcachedRegionFactory = new MemcachedRegionFactory();
    memcachedRegionFactory.start(settings, properties);

    ehCacheRegionFactory = new EhCacheRegionFactory();
    ehCacheRegionFactory.start(settings, properties);

    String ehCacheRegionFilter = properties.getProperty(CONFIG_EHCACHE_REGION_FILTER);
    if (ehCacheRegionFilter != null && !ehCacheRegionFilter.isEmpty()) {
      ehCacheFilterPattern = Pattern.compile(ehCacheRegionFilter);
    }
  }

  @Override
  public void stop() {
    memcachedRegionFactory.stop();
    ehCacheRegionFactory.stop();
  }

  @Override
  public boolean isMinimalPutsEnabledByDefault() {
    return false;
  }

  @Override
  public AccessType getDefaultAccessType() {
    return AccessType.READ_WRITE;
  }

  @Override
  public long nextTimestamp() {
    return memcachedRegionFactory.nextTimestamp();
  }

  @Override
  public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    if (ehCacheFilterPattern.matcher(regionName).matches()) {
      return ehCacheRegionFactory.buildEntityRegion(regionName, properties, metadata);
    }
    return memcachedRegionFactory.buildEntityRegion(regionName, properties, metadata);
  }

  @Override
  public NaturalIdRegion buildNaturalIdRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    if (ehCacheFilterPattern.matcher(regionName).matches()) {
      return ehCacheRegionFactory.buildNaturalIdRegion(regionName, properties, metadata);
    }
    return memcachedRegionFactory.buildNaturalIdRegion(regionName, properties, metadata);
  }

  @Override
  public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    if (ehCacheFilterPattern.matcher(regionName).matches()) {
      return ehCacheRegionFactory.buildCollectionRegion(regionName, properties, metadata);
    }
    return memcachedRegionFactory.buildCollectionRegion(regionName, properties, metadata);
  }

  @Override
  public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
    if (ehCacheFilterPattern.matcher(regionName).matches()) {
      return ehCacheRegionFactory.buildQueryResultsRegion(regionName, properties);
    }
    return memcachedRegionFactory.buildQueryResultsRegion(regionName, properties);
  }

  @Override
  public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
    if (ehCacheFilterPattern.matcher(regionName).matches()) {
      return ehCacheRegionFactory.buildTimestampsRegion(regionName, properties);
    }
    return memcachedRegionFactory.buildTimestampsRegion(regionName, properties);
  }
}
