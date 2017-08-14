package com.akhalikov.backend.utils.executors;

/**
 * Problem:
 *
 * method scheduleAtFixedRate of java.util.concurrent.ScheduledExecutorService
 * works in such a way that if any execution of the task encounters an exception,
 * subsequent executions are suppressed.
 *
 * This implementation guarantees that executions will not stop after exception.
 */
public class ResilientScheduledExecutor {
}
