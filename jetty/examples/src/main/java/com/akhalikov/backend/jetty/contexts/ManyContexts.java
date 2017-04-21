package com.akhalikov.backend.jetty.contexts;

import com.akhalikov.backend.jetty.AbstractJettyServer;
import com.akhalikov.backend.jetty.handlers.hellohandler.HelloHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

/**
 * When many contexts are present, you can embed a ContextHandlerCollection to efficiently examine a
 * request URI to then select the matching ContextHandler(s) for the request.
 * The ManyContexts example shows how many such contexts you can configure.
 */
public class ManyContexts extends AbstractJettyServer {

  @Override
  protected void configure() {
    ContextHandler rootContext = new ContextHandler("/");
    rootContext.setHandler(new HelloHandler("Root Hello"));

    ContextHandler frContext = new ContextHandler("/fr");
    frContext.setHandler(new HelloHandler("Bonjoir"));

    ContextHandler ruContext = new ContextHandler("/ru");
    ruContext.setHandler(new HelloHandler("Привет"));

    ContextHandler virtualContext = new ContextHandler("/");
    virtualContext.setVirtualHosts(new String[] {"127.0.0.2"});
    virtualContext.setHandler(new HelloHandler("Virtual Hello"));

    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[] {frContext, ruContext, rootContext, virtualContext});

    server.setHandler(contexts);
  }

  public static void main(String[] args) throws Exception {
    new ManyContexts().start();
  }
}
