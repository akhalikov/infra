package com.akhalikov.backend.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WrappingScheduledExecutor implements ScheduledExecutorService {
  private static final Logger logger = LoggerFactory.getLogger(WrappingScheduledExecutor.class);

  private final ScheduledExecutorService scheduledExecutor;

  public WrappingScheduledExecutor() {
    this("wrapping scheduled executor", 1);
  }

  public WrappingScheduledExecutor(String threadName) {
    this(threadName, 1);
  }

  public WrappingScheduledExecutor(String threadName, int threadsNum) {
    ThreadFactory threadFactory = (Runnable runnable) -> {
      Thread thread = new Thread(runnable, threadName);
      thread.setDaemon(true);
      return thread;
    };

    scheduledExecutor = Executors.newScheduledThreadPool(threadsNum, threadFactory);
  }

  @Override
  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
    return scheduledExecutor.schedule(command, delay, unit);
  }

  @Override
  public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
    return scheduledExecutor.schedule(callable, delay, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
    Runnable wrappedCommand = wrap(command);
    return scheduledExecutor.scheduleAtFixedRate(wrappedCommand, initialDelay, period, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
    Runnable wrappedCommand = wrap(command);
    return scheduledExecutor.scheduleAtFixedRate(wrappedCommand, initialDelay, delay, unit);
  }

  @Override
  public void shutdown() {
    scheduledExecutor.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow() {
    return scheduledExecutor.shutdownNow();
  }

  @Override
  public boolean isShutdown() {
    return scheduledExecutor.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return scheduledExecutor.isTerminated();
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return scheduledExecutor.awaitTermination(timeout, unit);
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return scheduledExecutor.submit(task);
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    return scheduledExecutor.submit(task, result);
  }

  @Override
  public Future<?> submit(Runnable task) {
    return scheduledExecutor.submit(task);
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
    return scheduledExecutor.invokeAll(tasks);
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
    return scheduledExecutor.invokeAll(tasks, timeout, unit);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
    return scheduledExecutor.invokeAny(tasks);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return scheduledExecutor.invokeAny(tasks, timeout, unit);
  }

  @Override
  public void execute(Runnable command) {
    scheduledExecutor.execute(command);
  }

  private static Runnable wrap(Runnable runnable) {
    return () -> {
      try {
        runnable.run();
      } catch (RuntimeException e) {
        logger.error("Failed to run task: {}", e.toString(), e);
      }
    };
  }
}
