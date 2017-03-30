package com.akhalikov.backend.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.proxy.LazyInitializer;

import java.lang.reflect.Field;

public class HibernateUtils {
  public static SessionFactory createSessionFactory() {
    StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
        .build();

   return createSessionFactory(standardServiceRegistry);
  }

  private static SessionFactory createSessionFactory(StandardServiceRegistry serviceRegistry) {
    Metadata metadata = new MetadataSources(serviceRegistry)
        .addAnnotatedClass(com.akhalikov.backend.users.User.class)
        .getMetadataBuilder()
        .build();

    return metadata.getSessionFactoryBuilder()
        .build();
  }

  public static LazyInitializer getEntityProxy(Object entity) {
    try {
      Field proxyHandlerField = entity.getClass().getDeclaredField("handler");
      proxyHandlerField.setAccessible(true);
      return (LazyInitializer) proxyHandlerField.get(entity);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Cannot access field handler", e);
    }
  }

  private HibernateUtils() {
  }
}
