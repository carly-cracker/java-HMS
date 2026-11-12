package demoTests;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import LabRequest;
import Patient;

public class LabQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<LabRequest> queue = new LinkedBlockingQueue<>();

        Thread doctor = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Patient p = new Patient(i, "Patient" + i, "Fever");
                    LabRequest req = new LabRequest(p, "Malaria test");
                    queue.put(req);
                    System.out.println("Doctor queued: " + req);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Doctor");

        Thread tech = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    LabRequest req = queue.take();
                    System.out.println("Lab tech processing: " + req);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "LabTech");

        doctor.start();
        tech.start();
        doctor.join();
        tech.join();
        System.out.println("All lab requests handled");
    }
}