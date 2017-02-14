package com.akhalikov.integration;

import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultLoadEventListener;
import org.hibernate.event.spi.LoadEvent;

@SuppressWarnings("unused")
public class OverridingLoadEventListener extends DefaultLoadEventListener {
  @Override
  public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException {
    String entityClassName = event.getEntityClassName();
    Object entityId = event.getEntityId();

    System.out.println("OverridingLoadEventListener: loading entity=" + entityClassName + ", id=" + entityId.toString());
    super.onLoad(event, loadType);
  }
}
