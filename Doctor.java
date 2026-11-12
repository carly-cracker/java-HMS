// Implementing runnable interface allowing real list rather than hardcoded 
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Doctor implements Runnable{
    //Implements Runnable so this class can be run safely inside its own background Thread
    private final String name;
    private final BlockingQueue<LabRequest> labQueue;
    private final List<Patient> patients;

    public Doctor(String name, BlockingQueue<LabRequest> labQueue, List<Patient> patients){
        this.name=name;
        this.labQueue=labQueue;
        this.patients=patients;
    }

    @Override
    public void run() {
        try {
            for (Patient patient : patients) {
                LabRequest request = new LabRequest(patient, "Malaria test");
                labQueue.put(request);
                
                System.out.println("Dr. " + name + " queued " + request);
                
                
                Thread.sleep(200);// we Simulated this with a sleep to mimic doctor attending next patient like real world scenario
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
    
    
// this is for producer in the producer-consumer pattern, where doctor produces lab request to the lab tech.