//ReentrantLock to ensure that only one thread can modify or read the count at any given time.
import java.util.concurrent.locks.ReentrantLock;

public class PatientCounter {
    private final ReentrantLock lock = new ReentrantLock();
    private int treated = 0;

    public void recordTreated() {
        lock.lock();
        try {
            treated++;
        } finally {
            lock.unlock();
        }
    }

    public int getTreated() {
        lock.lock();
        try {
            return treated;
        } finally {
            lock.unlock();
        }
    }
}