package com.akhalikov.integration;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

public class MyLocalSessionFactoryBean extends LocalSessionFactoryBean {
  @SuppressWarnings("unchecked")
  public void registerListeners() {
    SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) getObject();

    EventListenerRegistry registry = sessionFactoryImpl
        .getServiceRegistry()
        .getService(EventListenerRegistry.class);

    registry.setListeners(EventType.LOAD, MyLoadEventListener.class);
  }
}
