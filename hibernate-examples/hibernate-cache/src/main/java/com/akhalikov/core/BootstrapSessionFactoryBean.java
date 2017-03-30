package com.akhalikov.core;

import static com.akhalikov.core.HibernateServiceRegistryBuilder.createBootstrapServiceRegistry;
import static com.akhalikov.core.HibernateServiceRegistryBuilder.createStandardServiceRegistry;
import com.akhalikov.integration.ListenersIntegrator;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

public class BootstrapSessionFactoryBean extends LocalSessionFactoryBean {
  private final ListenersIntegrator listenersIntegrator = new ListenersIntegrator();

  @Override
  protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
    BootstrapServiceRegistry bootstrapRegistry = createBootstrapServiceRegistry(getResourceLoader(), listenersIntegrator);

    StandardServiceRegistry serviceRegistry = createStandardServiceRegistry(bootstrapRegistry, sfb.getProperties());

    return sfb.buildSessionFactory(serviceRegistry);
  }
}
