package com.akhalikov.integration;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import javax.inject.Inject;

public class HibernateSpringIntegrator implements Integrator {
  @Inject
  private MyLoadEventListener myLoadEventListener;

  @Override
  public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    final EventListenerRegistry registry = serviceRegistry.getService(EventListenerRegistry.class);
    registry.setListeners(EventType.LOAD, myLoadEventListener);
  }

  @Override
  public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
  }
}
