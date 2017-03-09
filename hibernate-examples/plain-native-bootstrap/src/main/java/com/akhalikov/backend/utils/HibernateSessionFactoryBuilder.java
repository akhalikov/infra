package com.akhalikov.backend.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;

import javax.sql.DataSource;
import java.util.Properties;

public class HibernateSessionFactoryBuilder {

  public static SessionFactory createSessionFactory(Properties properties) {
    DataSource dataSource = DataSourceFactory.createPGSimpleDataSource(properties);

    StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
        .configure("hibernate.cfg.xml")
        .applySetting(AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, "proxy") // "proxy" is the default, but for explicitness
        .applySetting(AvailableSettings.DATASOURCE, dataSource) // it seems that better way is to use ConnectionProvider
        .build();

    Metadata metadata = new MetadataSources(standardServiceRegistry)
        .getMetadataBuilder()
        .build();

    return metadata.getSessionFactoryBuilder()
        .build();
  }

  private HibernateSessionFactoryBuilder() {
  }
}
