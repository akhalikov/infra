package com.akhalikov.backend.jetty.spring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  ThreadPool jettyThreadPool() {
    return new QueuedThreadPool(8, 2);
  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  Server jettyServer(ThreadPool jettyThreadPool) {
    Server server = createJettyServer(jettyThreadPool);

    WebAppContext webApp = createWebAppContext();
    server.setHandler(webApp);

    return server;
  }

  private static Server createJettyServer(ThreadPool threadPool) {
    Server server = new Server(threadPool);
    server.setStopAtShutdown(true);
    server.setStopTimeout(5000);

    int acceptors = -1; // -1 means auto
    int selectors = -1; // -1 means auto
    int port = 8080;

    ServerConnector serverConnector = new ServerConnector(server, acceptors, selectors);
    serverConnector.setPort(port);
    server.addConnector(serverConnector);

    return server;
  }

  private static WebAppContext createWebAppContext() {
    final WebAppContext webApp = new WebAppContext();
    webApp.setContextPath("/");
    webApp.setResourceBase("src/main/webapp");
    webApp.setDescriptor("target/web.xml");
    webApp.setClassLoader(Thread.currentThread().getContextClassLoader());
    return webApp;
  }
}
