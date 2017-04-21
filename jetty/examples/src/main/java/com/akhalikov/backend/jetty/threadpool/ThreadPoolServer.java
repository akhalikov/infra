package com.akhalikov.backend.jetty.threadpool;

import com.akhalikov.backend.jetty.AbstractJettyServer;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

public class ThreadPoolServer extends AbstractJettyServer {
  private static ThreadPool threadPool;

  @Override
  protected void configure() throws Exception {
    threadPool = configureThreadPool();


  }

  private ThreadPool configureThreadPool() throws Exception {
    int minThreads = 4;
    int maxThreads = 8;
    int idleTimeoutMs = 60_000;

    QueuedThreadPool queuedThreadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeoutMs);
    queuedThreadPool.start();
    return queuedThreadPool;
  }
}
