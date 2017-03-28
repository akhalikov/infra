package com.akhalikov.backend.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.SQLException;

public class HibernateSessionFactoryBuilder {

  public static SessionFactory createSessionFactory() throws SQLException {
    StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
        .build();

    Metadata metadata = new MetadataSources(standardServiceRegistry)
        .addAnnotatedClass(com.akhalikov.backend.users.User.class)
        .getMetadataBuilder()
        .build();

    return metadata.getSessionFactoryBuilder()
        .build();
  }

  private HibernateSessionFactoryBuilder() {
  }
}
