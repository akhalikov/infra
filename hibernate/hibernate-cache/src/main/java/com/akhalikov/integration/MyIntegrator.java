package com.akhalikov.integration;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyIntegrator implements Integrator {
  private static final Logger log = LoggerFactory.getLogger(MyIntegrator.class);

  @Override
  public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
  }

  @Override
  public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    final EventListenerRegistry registry = serviceRegistry.getService(EventListenerRegistry.class);

    System.out.println("Setting listener for LOAD event: MyLoadEventListener");
    registry.setListeners(EventType.LOAD, MyLoadEventListener.class);
  }

  @Override
  public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory,
                        SessionFactoryServiceRegistry serviceRegistry) {
  }
}
