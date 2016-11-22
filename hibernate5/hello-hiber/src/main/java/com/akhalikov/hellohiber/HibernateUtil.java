package com.akhalikov.hellohiber;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

class HibernateUtil {
  private static final SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        .configure() // configures settings from hibernate.cfg.xml
        .build();

    try {
      return new MetadataSources(registry)
          .buildMetadata()
          .buildSessionFactory();
    } catch (Throwable ex) {
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy(registry);
      throw new ExceptionInInitializerError(ex);
    }
  }

  static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  static void shutdown() {
    getSessionFactory().close();
  }
}