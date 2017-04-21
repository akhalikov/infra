package com.akhalikov.backend.jetty.webapp;

import com.akhalikov.backend.jetty.AbstractJettyServer;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.lang.management.ManagementFactory;

/**
 * Embedding Web Applications
 *
 * A WebAppContext is an extension of a ServletContextHandler that uses the standard layout and web.xml
 * to configure the servlets, filters and other features from a web.xml and/or annotations.
 *
 * The following OneWebApp example configures the Jetty test webapp.
 * Web applications can use resources the container provides.
 */
public class JmxWebApp extends AbstractJettyServer {

  @Override
  protected void configure() {
    MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
    server.addBean(mbContainer);

    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    File warFile = new File("target");
    webapp.setWar(warFile.getAbsolutePath());

    server.setHandler(webapp);
  }

  public static void main(String[] args) throws Exception {
    new JmxWebApp().start();
  }
}
