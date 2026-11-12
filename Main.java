import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        PatientRepository repository = new PatientRepository();
        for (int i = 1; i <= 100; i++) {
            repository.add(new Patient(i, "Patient" + i, "Fever"));
        }

        MedicineInventory inventory = new MedicineInventory();
        inventory.addStock("Paracetamol", 50);

        BlockingQueue<LabRequest> labRequestQueue = new ArrayBlockingQueue<>(20);
        BlockingQueue<LabResult> resultQueue = new ArrayBlockingQueue<>(20);
        Semaphore labMachines = new Semaphore(2, true);   // 3 technicians share 2 machines
        PatientCounter counter = new PatientCounter();

        // 10 nurses + 3 technicians + 5 doctors + 2 pharmacists = 20 threads
        ExecutorService pool = Executors.newFixedThreadPool(20);

        List<Future<?>> nurseFutures = new ArrayList<>();
        for (int n = 0; n < 10; n++) {
            int from = n * 10 + 1;
            nurseFutures.add(pool.submit(
                new Nurse("Nurse" + (n + 1), repository, labRequestQueue, from, from + 9)));
        }

        List<Future<?>> techFutures = new ArrayList<>();
        for (int t = 1; t <= 3; t++) {
            techFutures.add(pool.submit(
                new LabTech("Tech" + t, labRequestQueue, labMachines, resultQueue)));
        }

        for (int d = 1; d <= 5; d++) {
            pool.submit(new Doctor("Doctor" + d, resultQueue));
        }

        pool.submit(new Pharmacist("Pharm1", inventory, 1, 50, counter, repository));
        pool.submit(new Pharmacist("Pharm2", inventory, 51, 100, counter, repository));

        // Shutdown in order: nurses -> technicians -> doctors
        for (Future<?> f : nurseFutures) f.get();
        for (int i = 0; i < 3; i++) labRequestQueue.put(LabRequest.NO_ORDER);

        for (Future<?> f : techFutures) f.get();
        for (int i = 0; i < 5; i++) resultQueue.put(LabResult.NO_ORDER);

        pool.shutdown();
        if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }

        // Consistency check
        int consistent = 0;
        for (PatientRecord r : repository.all()) {
            if (r.getVitalsCount() == 1 && r.getResultCount() == 1 && r.getNoteCount() == 1) {
                consistent++;
            }
        }
        System.out.println("-----");
        System.out.println("Patients registered:      " + repository.size());
        System.out.println("Consistent records:       " + consistent + "/100");
        System.out.println("Paracetamol left:         " + inventory.getStock("Paracetamol"));
        System.out.println("Patients given medicine:  " + counter.getTreated());
        System.out.println("Hospital closed");
    }
}