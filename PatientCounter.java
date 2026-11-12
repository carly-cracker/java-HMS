//ReentrantLock to ensure that only one thread can modify or read the count at any given time.
import java.util.concurrent.locks.ReentrantLock;

public class PatientCounter {
    // Create an explicit lock object to control thread access and shared treated counter
    private final ReentrantLock lock = new ReentrantLock();
    private int treated = 0;

    // method for safe incrementing
    public void recordTreated(){
        lock.lock();
        try{
            treated++;
        } finally{
            lock.unlock(); //to ensure there is no deadlock.
        }
    }
// method for safe reading
    public int getTreated(){
        lock.lock();
        try{
            return treated;
        } finally {
            lock.unlock();
        }
    }

}