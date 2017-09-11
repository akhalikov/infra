package akhalikov.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import com.datastax.driver.core.policies.ReconnectionPolicy;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class ClusterFactory {

  public static Cluster createCassandraCluster(Properties properties) {
    Cluster cluster = Cluster.builder()
        .addContactPointsWithPorts(parseContactPoints(properties))
        .withProtocolVersion(ProtocolVersion.V3)
        .withPoolingOptions(parsePoolingOptions(properties))
        .withReconnectionPolicy(parseReconnectionPolicy(properties))
        .withSocketOptions(parseSocketOptions(properties))
        .withQueryOptions(new QueryOptions().setConsistencyLevel(ConsistencyLevel.QUORUM))
        .build();

    return cluster;
  }

  private static Set<InetSocketAddress> parseContactPoints(Properties properties) {
    String clusterNodes = properties.getProperty("nodes");
    if (clusterNodes == null) {
      throw new IllegalArgumentException("Can't find 'nodes' parameter");
    }
    Set<InetSocketAddress> contactPoints = new HashSet<>();
    for (String rawAddress : clusterNodes.split(",")) {
      final String[] addressParts = rawAddress.trim().split(":");
      final String host = addressParts[0];
      final int port = addressParts.length > 1 ? parseInt(addressParts[1]) : ProtocolOptions.DEFAULT_PORT;
      InetSocketAddress contactPoint = new InetSocketAddress(host, port);
      contactPoints.add(contactPoint);
    }
    return contactPoints;
  }

  private static PoolingOptions parsePoolingOptions(Properties properties) {
    PoolingOptions poolingOptions = new PoolingOptions();
    String heartbeatIntervalSeconds = properties.getProperty("heartbeatIntervalSeconds", "10");
    poolingOptions.setHeartbeatIntervalSeconds(parseInt(heartbeatIntervalSeconds));
    return poolingOptions;
  }

  private static ReconnectionPolicy parseReconnectionPolicy(Properties properties) {
    String baseDelay = properties.getProperty("reconnectBaseDelayMs", "200");
    String maxDelay = properties.getProperty("reconnectMaxDelayMs", "5000");
    return new ExponentialReconnectionPolicy(parseInt(baseDelay), parseInt(maxDelay));
  }

  private static SocketOptions parseSocketOptions(Properties properties) {
    SocketOptions socketOptions = new SocketOptions();
    String connectTimeout = properties.getProperty("connectTimeoutMs");
    if (connectTimeout != null) {
      socketOptions.setConnectTimeoutMillis(parseInt(connectTimeout));
    }
    String readTimeout = properties.getProperty("readTimeoutPerNodeMs");
    if (readTimeout != null) {
      socketOptions.setReadTimeoutMillis(parseInt(readTimeout));
    }
    return socketOptions;
  }
}
