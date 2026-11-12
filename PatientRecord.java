import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class PatientRecord {
    private final Patient patient;
    private final ReentrantLock lock = new ReentrantLock(true); // fair: waiting threads go in order

    private int temperature;
    private int heartRate;
    private int vitalsCount = 0;
    private final List<String> notes = new ArrayList<>();
    private final List<String> testResults = new ArrayList<>();

    public PatientRecord(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() { return patient; }

    // Nurses: temperature and heart rate must always come from the same reading
    public void recordVitals(int temperature, int heartRate) {
        lock.lock();
        try {
            this.temperature = temperature;
            this.heartRate = heartRate;
            vitalsCount++;
        } finally {
            lock.unlock();
        }
    }

    // Doctors
    public void addNote(String doctorName, String note) {
        lock.lock();
        try {
            notes.add(doctorName + ": " + note);
        } finally {
            lock.unlock();
        }
    }

    // Doctors, once they retrieve a completed lab result
    public void addTestResult(String result) {
        lock.lock();
        try {
            testResults.add(result);
        } finally {
            lock.unlock();
        }
    }

    public int getVitalsCount() {
        lock.lock();
        try { return vitalsCount; } finally { lock.unlock(); }
    }

    public int getNoteCount() {
        lock.lock();
        try { return notes.size(); } finally { lock.unlock(); }
    }

    public int getResultCount() {
        lock.lock();
        try { return testResults.size(); } finally { lock.unlock(); }
    }
}