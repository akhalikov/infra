package waitnotify;

/**
 * @author ahalikov
 */
public class RandomRun {

    public static void main(String[] args) {
        new RandomRun().example();
    }

    public static void example() {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
            }).start();
        }
    }
}
