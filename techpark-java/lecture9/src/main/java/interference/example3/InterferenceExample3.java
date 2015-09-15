package interference.example3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Example of thread interference
 *
 * https://www.youtube.com/watch?t=385&v=dLDhB6SRXzw
 */
class InterferenceExample3 {

    private static final int HUNDRED_MILLION = 100_000_000;
    private AtomicInteger counter = new AtomicInteger();

    public boolean stop() {
        return counter.incrementAndGet() > HUNDRED_MILLION;
    }

    public void runExample() throws InterruptedException {
        InterferenceThread t1 = new InterferenceThread(this);
        InterferenceThread t2 = new InterferenceThread(this);
        t1.start();
        t2.start();
        t1.join();
        System.out.println("Expected=" + HUNDRED_MILLION);
        System.out.println("Result=" + t1.getI());
        System.out.println("Counter=" + counter.get());
    }

    public static void main(String[] args) throws Exception {
        (new InterferenceExample3()).runExample();
    }
}
