package com.akhalikov.backend.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import static com.google.inject.name.Names.named;
import org.postgresql.ds.PGSimpleDataSource;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Prod-only guice module
 */
public class ProdModule extends AbstractModule {

  @Override
  protected void configure() {
    //example: bind(Abstract.class).annotatedWith(Names.named("foo")).to(FooImplementation.class);

    bind(String.class).annotatedWith(named("dbUrl")).toInstance("jdbc:postgresql:backend");
    bind(String.class).annotatedWith(named("dbUser")).toInstance("backend");
    bind(String.class).annotatedWith(named("dbPassword")).toInstance("123");
  }

  @Provides
  @Singleton
  static DataSource provideDataSource(
      @Named("dbUrl") final String url,
      @Named("dbUser") final String user,
      @Named("dbPassword") final String pasword) throws SQLException {

    final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setUrl(url);
    dataSource.setUser(user);
    dataSource.setPassword(pasword);
    return dataSource;
  }
}
