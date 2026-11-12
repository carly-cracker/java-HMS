import java.util.ArrayList;
import java.util.List;

public class RecordDemo {
    public static void main(String[] args) throws InterruptedException {
        PatientRepository repo = new PatientRepository();
        for (int i = 1; i <= 100; i++) {
            repo.add(new Patient(i, "Patient" + i, "Fever"));
        }
        System.out.println("Patients registered: " + repo.size());

        PatientRecord shared = repo.get(1);
        List<Thread> threads = new ArrayList<>();

        for (int d = 1; d <= 5; d++) {
            final int id = d;
            threads.add(new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    shared.addNote("Dr" + id, "note " + j);
                }
            }));
        }
        for (int n = 1; n <= 10; n++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    shared.recordVitals(37, 80);
                }
            }));
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Notes:  " + shared.getNoteCount() + " (should be 5000)");
        System.out.println("Vitals: " + shared.getVitalsCount() + " (should be 10000)");
    }
}