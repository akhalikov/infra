package com.akhalikov;

import com.akhalikov.cassandra.ClusterFactory;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.util.Properties;

public class TimelineClient implements AutoCloseable {
  private final Cluster cluster;
  private final Session session;

  public TimelineClient(Properties properties) {
    cluster = ClusterFactory.createCassandraCluster(properties);
    session = cluster.connect("user_timeline");
  }

  public Session getSession() {
    return session;
  }

  @Override
  public void close() throws Exception {
    session.close();
  }
}
