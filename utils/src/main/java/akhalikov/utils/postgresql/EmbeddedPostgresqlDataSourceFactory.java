package akhalikov.utils.postgresql;

import akhalikov.utils.FilesystemUtils;
import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.DriverManagerDataSource;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

public class EmbeddedPostgresqlDataSourceFactory {
  private static final String EMBEDDED_PG_DIR = "embedded-pg";
  private static final String EMBEDDED_PG_DIR_PROPERTY = "ness.embedded-pg.dir";

  public static DataSource create() throws SQLException {
    EmbeddedPostgres pgInstance = createEmbeddedPostgresInstance();

    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(false);
    driverManagerDataSource.setJdbcUrl("jdbc:log4jdbc:postgresql://localhost:" + pgInstance.getPort() + "/postgres");
    driverManagerDataSource.setUser("postgres");

    Properties pooledProps = new Properties();
    pooledProps.setProperty("acquireIncrement", "1");
    return DataSources.pooledDataSource(driverManagerDataSource, pooledProps);
  }

  private static EmbeddedPostgres createEmbeddedPostgresInstance() {
    try {
      setEmbeddedPgDir();
      return EmbeddedPostgres.builder()
        .setServerConfig("autovacuum", "off")
        .setLocaleConfig("lc-collate", "C")
        .start();
    } catch (IOException e) {
      throw new IllegalStateException("Can't start embedded Postgres", e);
    }
  }

  private static void setEmbeddedPgDir() throws IOException {
    Path tmpfsPath = FilesystemUtils.getTmpfsPath();
    if (tmpfsPath != null) {
      Path pgPath = Paths.get(tmpfsPath.toString(), EMBEDDED_PG_DIR);
      if (Files.notExists(pgPath)) {
        Files.createDirectory(pgPath);
      }
      System.setProperty(EMBEDDED_PG_DIR_PROPERTY, pgPath.toString());
    }
  }

  private EmbeddedPostgresqlDataSourceFactory() {
  }
}
