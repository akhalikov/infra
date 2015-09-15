package waitnotify;

/**
 * Sequential run organized using wait/notify
 *
 * @author ahalikov
 */
public class SeriesRun {

    public static void main(String[] args) {
        new SeriesRun().example();
    }

    public static void example() {
        final Object waitObject = new Object();
        for (int i = SeriesThread.currentMax; i <= 10; i++) {
            new Thread(new SeriesThread(i, waitObject))
                    .start();
        }
    }

    private static class SeriesThread implements Runnable {
        private static int currentMax = 1;
        private final int id;
        private final Object waitObject;

        public SeriesThread(int id, Object waitObject) {
            this.id = id;
            this.waitObject = waitObject;
        }

        @Override
        public void run() {
            try {
                //System.out.println("Start run of thread: " + id);
                synchronized (waitObject) {
                    while (id > currentMax) {
                        waitObject.wait();
                    }

                    currentMax++;
                    System.out.println("Hello from thread: " + id);
                    waitObject.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
