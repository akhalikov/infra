package interference.example2;

final class InterferenceThread extends Thread {

    private final InterferenceExample2 checker;
    private static volatile int i;

    public InterferenceThread(InterferenceExample2 checker) {
        this.checker = checker;
    }

    @Override
    public void run() {
        while (!checker.stop()) {
            increment();
        }
    }

    public static synchronized void increment() {
        i++;
    }

    public int getI() {
        return i;
    }
}
