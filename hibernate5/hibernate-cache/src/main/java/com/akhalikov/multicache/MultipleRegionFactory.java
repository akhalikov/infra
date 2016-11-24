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

import java.util.Properties;
import java.util.regex.Pattern;

public class MultipleRegionFactory implements RegionFactory {
  private static final String EHCACHE_REGION_FILTER_KEY = "local";
  private static final Pattern EHCACHE_REGION_FILTER_PATTERN = Pattern.compile(EHCACHE_REGION_FILTER_KEY);

  private MemcachedRegionFactory defaultRegionFactory;
  private EhCacheRegionFactory ehCacheRegionFactory;

  @Override
  public void start(SessionFactoryOptions settings, Properties properties) throws CacheException {
    defaultRegionFactory = new MemcachedRegionFactory();
    defaultRegionFactory.start(settings, properties);

    ehCacheRegionFactory = new EhCacheRegionFactory();
    ehCacheRegionFactory.start(settings, properties);
  }

  @Override
  public void stop() {
    defaultRegionFactory.stop();
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
    return defaultRegionFactory.nextTimestamp();
  }

  @Override
  public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    return isEhcacheRegion(regionName)
        ? ehCacheRegionFactory.buildEntityRegion(regionName, properties, metadata)
        : defaultRegionFactory.buildEntityRegion(regionName, properties, metadata);
  }

  @Override
  public NaturalIdRegion buildNaturalIdRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    return isEhcacheRegion(regionName)
        ? ehCacheRegionFactory.buildNaturalIdRegion(regionName, properties, metadata)
        : defaultRegionFactory.buildNaturalIdRegion(regionName, properties, metadata);
  }

  @Override
  public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    return isEhcacheRegion(regionName)
        ? ehCacheRegionFactory.buildCollectionRegion(regionName, properties, metadata)
        : defaultRegionFactory.buildCollectionRegion(regionName, properties, metadata);
  }

  @Override
  public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
    return isEhcacheRegion(regionName)
        ? ehCacheRegionFactory.buildQueryResultsRegion(regionName, properties)
        : defaultRegionFactory.buildQueryResultsRegion(regionName, properties);
  }

  @Override
  public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
    return isEhcacheRegion(regionName)
        ? ehCacheRegionFactory.buildTimestampsRegion(regionName, properties)
        : defaultRegionFactory.buildTimestampsRegion(regionName, properties);
  }

  private static boolean isEhcacheRegion(String regionName) {
    return EHCACHE_REGION_FILTER_PATTERN.matcher(regionName).matches();
  }
}
