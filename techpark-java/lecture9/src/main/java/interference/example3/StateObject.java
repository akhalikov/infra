package interference.example3;

/**
 * @author ahalikov
 */
public class StateObject {

    private int i;

    public int getI() {
        return i;
    }

    public synchronized void increment() {
        i++;
    }
}
