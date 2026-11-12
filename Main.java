import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BlockingQueue<LabRequest> labQueue = new ArrayBlockingQueue<>(5);
        MedicineInventory inventory = new MedicineInventory();
        inventory.addStock("Paracetamol", 20);

        List<Patient> group1 = List.of(
            new Patient(1, "Will", "Fever"),
            new Patient(2, "Brian", "Fever"),
            new Patient(3, "Cynthia", "Headache"));
        List<Patient> group2 = List.of(
            new Patient(4, "David", "Fever"),
            new Patient(5, "Esther", "Cough"));

        ExecutorService pool = Executors.newFixedThreadPool(8);
        Semaphore labMachines = new Semaphore(2, true); //tried also with 1 then 2 and then 3 machines (the more the machines the faster the processing) 

        Future<?> doctor1 = pool.submit(new Doctor("Phill", labQueue, group1));
        Future<?> doctor2 = pool.submit(new Doctor("Maria", labQueue, group2));
        pool.submit(new LabTech("Carly", labQueue, labMachines));
        pool.submit(new LabTech("Milton", labQueue, labMachines));
        pool.submit(new Nurse("Wanjiku", group1));
        pool.submit(new Nurse("Clariet", group2));
        pool.submit(new Pharmacist("Achieng", inventory, group1));
        pool.submit(new Pharmacist("kenney", inventory, group2));

        // Wait for the doctors, then tell each technician to stop
        doctor1.get();
        doctor2.get();
        labQueue.put(LabRequest.NO_ORDER);
        labQueue.put(LabRequest.NO_ORDER);

        pool.shutdown();
        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }

        System.out.println("Paracetamol left: " + inventory.getStock("Paracetamol"));
        System.out.println("Hospital closed");
    }
}