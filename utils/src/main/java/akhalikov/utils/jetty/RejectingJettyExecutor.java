package akhalikov.utils.jetty;

import org.eclipse.jetty.util.thread.ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public class RejectingJettyExecutor implements Executor {
  private final ThreadPool threadPool;

  public RejectingJettyExecutor(ThreadPool threadPool) {
    this.threadPool = threadPool;
  }

  @Override
  public void execute(Runnable command) {
    if (threadPool.isLowOnThreads()) {
      throw new RejectedExecutionException("low on threads");
    }
    threadPool.execute(command);
  }
}
