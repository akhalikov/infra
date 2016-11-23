package com.akhalikov.multicache;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cache.spi.access.AccessType;

import java.util.Properties;

public class MultipleRegionFactory implements RegionFactory {
  private RegionFactory memcachedRegionFactory;

  @Override
  public void start(SessionFactoryOptions settings, Properties properties) throws CacheException {

  }

  @Override
  public void stop() {

  }

  @Override
  public boolean isMinimalPutsEnabledByDefault() {
    return false;
  }

  @Override
  public AccessType getDefaultAccessType() {
    return null;
  }

  @Override
  public long nextTimestamp() {
    return 0;
  }

  @Override
  public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    return null;
  }

  @Override
  public NaturalIdRegion buildNaturalIdRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    return null;
  }

  @Override
  public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    return null;
  }

  @Override
  public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
    return null;
  }

  @Override
  public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
    return null;
  }
}
