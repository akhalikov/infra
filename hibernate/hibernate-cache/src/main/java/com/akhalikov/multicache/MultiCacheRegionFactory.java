package com.akhalikov.multicache;

import com.mc.hibernate.memcached.MemcachedRegionFactory;
import org.hibernate.boot.spi.SessionFactoryOptions;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class MultiCacheRegionFactory implements RegionFactory {
  private static final String CONFIG_EHCACHE_REGION_FILTERS = "hibernate.multicache.ehcache.region_filters";

  private MemcachedRegionFactory memcachedRegionFactory;
  private EhCacheRegionFactory ehCacheRegionFactory;
  private final List<Pattern> ehCacheFilterPatterns = new ArrayList<>();

  @Override
  public void start(SessionFactoryOptions settings, Properties properties) throws CacheException {
    memcachedRegionFactory = new MemcachedRegionFactory();
    memcachedRegionFactory.start(settings, properties);

    String ehCacheRegionFilters = properties.getProperty(CONFIG_EHCACHE_REGION_FILTERS);
    if (ehCacheRegionFilters != null) {
      for (String filter: ehCacheRegionFilters.split(",")) {
        ehCacheFilterPatterns.add(Pattern.compile(filter));
      }
      ehCacheRegionFactory = new EhCacheRegionFactory();
      ehCacheRegionFactory.start(settings, properties);
    }
  }

  @Override
  public void stop() {
    memcachedRegionFactory.stop();

    if (ehCacheRegionFactory != null) {
      ehCacheRegionFactory.stop();
    }
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
    for (Pattern pattern: ehCacheFilterPatterns) {
      if (pattern.matcher(regionName).matches()) {
        return ehCacheRegionFactory.buildEntityRegion(regionName, properties, metadata);
      }
    }
    return memcachedRegionFactory.buildEntityRegion(regionName, properties, metadata);
  }

  @Override
  public NaturalIdRegion buildNaturalIdRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    for (Pattern pattern: ehCacheFilterPatterns) {
      if (pattern.matcher(regionName).matches()) {
        return ehCacheRegionFactory.buildNaturalIdRegion(regionName, properties, metadata);
      }
    }
    return memcachedRegionFactory.buildNaturalIdRegion(regionName, properties, metadata);
  }

  @Override
  public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    for (Pattern pattern: ehCacheFilterPatterns) {
      if (pattern.matcher(regionName).matches()) {
        return ehCacheRegionFactory.buildCollectionRegion(regionName, properties, metadata);
      }
    }
    return memcachedRegionFactory.buildCollectionRegion(regionName, properties, metadata);
  }

  @Override
  public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
    for (Pattern pattern: ehCacheFilterPatterns) {
      if (pattern.matcher(regionName).matches()) {
        return ehCacheRegionFactory.buildQueryResultsRegion(regionName, properties);
      }
    }
    return memcachedRegionFactory.buildQueryResultsRegion(regionName, properties);
  }

  @Override
  public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
    for (Pattern pattern: ehCacheFilterPatterns) {
      if (pattern.matcher(regionName).matches()) {
        return ehCacheRegionFactory.buildTimestampsRegion(regionName, properties);
      }
    }
    return memcachedRegionFactory.buildTimestampsRegion(regionName, properties);
  }
}
