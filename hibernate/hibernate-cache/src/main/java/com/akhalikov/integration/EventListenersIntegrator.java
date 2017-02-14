package com.akhalikov.integration;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

@SuppressWarnings("unchecked")
public class EventListenersIntegrator implements Integrator {
  @Override
  public void integrate(Metadata metadata,
                        SessionFactoryImplementor sessionFactory,
                        SessionFactoryServiceRegistry serviceRegistry) {
    final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

    eventListenerRegistry.setListeners(EventType.LOAD, OverridingLoadEventListener.class);
  }

  @Override
  public void disintegrate(SessionFactoryImplementor sessionFactory,
                           SessionFactoryServiceRegistry serviceRegistry) {
    // doing nothing
  }
}
