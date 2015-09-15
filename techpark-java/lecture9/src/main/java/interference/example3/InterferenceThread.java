package interference.example3;

final class InterferenceThread extends Thread {

    private final InterferenceExample3 checker;
    private static StateObject state = new StateObject();

    public InterferenceThread(InterferenceExample3 checker) {
        this.checker = checker;
    }

    @Override
    public void run() {
        while (!checker.stop()) {
            state.increment();
        }
    }

    public int getI() {
        return state.getI();
    }
}
