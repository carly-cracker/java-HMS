import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<LabRequest> labQueue = new ArrayBlockingQueue<>(5);

        List<Patient> group1 = List.of(
            new Patient(1, "Will", "Fever"),
            new Patient(2, "Brian", "Fever"),
            new Patient(3, "Cynthia", "Headache"));
        List<Patient> group2 = List.of(
            new Patient(4, "David", "Fever"),
            new Patient(5, "Esther", "Cough"));

        Thread doctor1 = new Thread(new Doctor("Phill", labQueue, group1));
        Thread doctor2 = new Thread(new Doctor("Maria", labQueue, group2));
        Thread tech1 = new Thread(new LabTech("Carl", labQueue));
        Thread tech2 = new Thread(new LabTech("Milton", labQueue));

        doctor1.start();
        doctor2.start();
        tech1.start();
        tech2.start();

        doctor1.join();
        doctor2.join();

        // One No_ORDER per technician so each one knows to stop
        labQueue.put(LabRequest.NO_ORDER);
        labQueue.put(LabRequest.NO_ORDER);

        tech1.join();
        tech2.join();
        System.out.println("All lab requests handled");
    }
}