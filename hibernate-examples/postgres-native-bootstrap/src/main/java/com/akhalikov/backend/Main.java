package com.akhalikov.backend;

import com.akhalikov.backend.users.UserHibernateDao;
import com.akhalikov.backend.utils.PGSimpleDataSourceFactory;
import com.akhalikov.backend.utils.PropertiesFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class Main {

  public static void main(String[] args) throws IOException {
    Properties properties = PropertiesFactory.load();

    DataSource dataSource = PGSimpleDataSourceFactory.createPGSimpleDataSource(properties);

    SessionFactory sessionFactory = createSessionFactory(dataSource);

    UserHibernateDao userDao = new UserHibernateDao(sessionFactory);

    System.out.println("users id db: " + userDao.getAll());
  }

  private static SessionFactory createSessionFactory(DataSource dataSource) {
    StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
        .configure("hibernate.cfg.xml")
        .applySetting(AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, "jdbc") // "jdbc" is the default, but for explicitness
        .applySetting(AvailableSettings.DATASOURCE, dataSource)
        .build();

    Metadata metadata = new MetadataSources(standardServiceRegistry)
        .getMetadataBuilder()
        .build();

    SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
        .build();

    return sessionFactory;
  }
}
