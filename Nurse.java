import java.util.concurrent.BlockingQueue;

public class Nurse implements Runnable { //MAKING NURSE RUNNABLE SO THAT IT CAN BE RUN IN ITS OWN THREAD
    private final String name;
    private final PatientRepository repository;
    private final BlockingQueue<LabRequest> labRequestQueue;
    private final int fromId;
    private final int toId;

    public Nurse(String name, PatientRepository repository,
                 BlockingQueue<LabRequest> labRequestQueue, int fromId, int toId) {
        this.name = name;
        this.repository = repository;
        this.labRequestQueue = labRequestQueue;
        this.fromId = fromId;
        this.toId = toId;
    }

    // public void recordVitals(Patient patient) {
    //     System.out.println("Nurse " + name + " recorded vitals for " + patient.getName());
    // }
    @Override
    public void run() {
        try {
            for (int id = fromId; id <= toId; id++) {
                PatientRecord record = repository.get(id);
                record.recordVitals(36 + id % 4, 70 + id % 30);
                LabRequest request = new LabRequest(record, "Malaria test");
                labRequestQueue.put(request);
                System.out.println(name + " recorded vitals and requested " + request);
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}