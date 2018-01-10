/*
package akhalikov.stats;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import ru.hh.metrics.Counters;
import ru.hh.metrics.StatsDSender;
import ru.hh.metrics.Tag;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class StatsMain {

  private static final Counters counters = new Counters(500);

  public static void main(String[] args) throws Exception {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    StatsDClient statsDClient = new NonBlockingStatsDClient(null, "localhost", 8125, 10_000);
    StatsDSender statsDSender = new StatsDSender(statsDClient, executorService);

    statsDSender.sendCountersPeriodically("client.requests", counters);

    for (int i = 0; i < 30; i++) {
      int counts = ThreadLocalRandom.current().nextInt(100, 1000);
      countAndWait(counts);
    }

    statsDClient.stop();
    executorService.shutdown();

    System.out.println("Done");
  }

  private static void countAndWait(int maxCounts) throws Exception {
    System.out.println("Counting statistics " + maxCounts);
    int[] statuses = {200, 499, 500, 503};
    for (int counter = 0; counter < maxCounts; counter++) {
      int i = ThreadLocalRandom.current().nextInt(statuses.length);
      int ms = ThreadLocalRandom.current().nextInt(10, 30);
      counters.add(ms, new Tag("appname", "client"), new Tag("upstream", "backend"), new Tag("status", String.valueOf(statuses[i])));
      TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(4));
    }
    TimeUnit.SECONDS.sleep(10);
  }
}
*/
