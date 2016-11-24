package com.akhalikov.multicache;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cache.spi.access.AccessType;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MultiCacheRegionFactory implements RegionFactory {

  private RegionFactory defaultRegionFactory;
  private final Map<Pattern, RegionFactory> patternToRegionFactoryMap = new HashMap<>();

  @Override
  public void start(SessionFactoryOptions settings, Properties properties) throws CacheException {
    final String defaultProviderKey = "default";

    MultiCacheConfig config = new MultiCacheConfig(properties);
    Map<String, Object> configValues = properties.entrySet()
      .stream()
      .collect(Collectors.toMap(String::valueOf, v -> v));

    String defaultRegionFactoryClassName = config.getRegionFactoryClass(defaultProviderKey);
    if (defaultRegionFactoryClassName == null) {
      throw new CacheException("default region factory is not set");
    }

    defaultRegionFactory = startRegionFactory(defaultRegionFactoryClassName, settings, configValues);

    for (String provider: config.getProviders()) {
      String regionFactoryClassName = config.getRegionFactoryClass(provider);
      if (regionFactoryClassName == null) {
        throw new CacheException("region factory class is not set for provider: " + provider);
      }

      String regionFilter = config.getRegionFilter(provider);
      if (regionFilter == null) {
        throw new CacheException("region filter is not set for provider: " + provider);
      }

      final Pattern pattern = Pattern.compile(regionFilter);
      final RegionFactory regionFactory = startRegionFactory(regionFactoryClassName, settings, configValues);

      patternToRegionFactoryMap.put(pattern, regionFactory);
    }
  }

  @Override
  public void stop() {
    defaultRegionFactory.stop();
    patternToRegionFactoryMap.forEach((pattern, regionFactory) -> regionFactory.stop());
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
    for (Map.Entry<Pattern, RegionFactory> entry: patternToRegionFactoryMap.entrySet()) {
      if (entry.getKey().matcher(regionName).matches()) {
        return entry.getValue().buildEntityRegion(regionName, properties, metadata);
      }
    }
    return defaultRegionFactory.buildEntityRegion(regionName, properties, metadata);
  }

  @Override
  public NaturalIdRegion buildNaturalIdRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    for (Map.Entry<Pattern, RegionFactory> entry: patternToRegionFactoryMap.entrySet()) {
      if (entry.getKey().matcher(regionName).matches()) {
        return entry.getValue().buildNaturalIdRegion(regionName, properties, metadata);
      }
    }
    return defaultRegionFactory.buildNaturalIdRegion(regionName, properties, metadata);
  }

  @Override
  public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
    for (Map.Entry<Pattern, RegionFactory> entry: patternToRegionFactoryMap.entrySet()) {
      if (entry.getKey().matcher(regionName).matches()) {
        return entry.getValue().buildCollectionRegion(regionName, properties, metadata);
      }
    }
    return defaultRegionFactory.buildCollectionRegion(regionName, properties, metadata);
  }

  @Override
  public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
    for (Map.Entry<Pattern, RegionFactory> entry: patternToRegionFactoryMap.entrySet()) {
      if (entry.getKey().matcher(regionName).matches()) {
        return entry.getValue().buildQueryResultsRegion(regionName, properties);
      }
    }
    return defaultRegionFactory.buildQueryResultsRegion(regionName, properties);
  }

  @Override
  public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
    for (Map.Entry<Pattern, RegionFactory> entry: patternToRegionFactoryMap.entrySet()) {
      if (entry.getKey().matcher(regionName).matches()) {
        return entry.getValue().buildTimestampsRegion(regionName, properties);
      }
    }
    return defaultRegionFactory.buildTimestampsRegion(regionName, properties);
  }

  private static RegionFactory startRegionFactory(final String className,
                                                  final SessionFactoryOptions settings,
                                                  final Map<String, Object> configValues) {
    RegionFactory regionFactory;
    try {
      Class<? extends RegionFactory> regionFactoryClass = Class.forName(className)
        .asSubclass(RegionFactory.class);

      regionFactory = regionFactoryClass.newInstance();
      regionFactory.start(settings, configValues);
      return regionFactory;

    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      throw new CacheException("Unable to load cache region factory class", e);
    }
  }
}
