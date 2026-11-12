// Implementing runnable interface allowing real list rather than hardcoded 
import java.util.concurrent.BlockingQueue;

public class Doctor implements Runnable{
    //Implements Runnable so this class can be run safely inside its own background Thread
    private final String name;
    private final BlockingQueue<LabResult> resultQueue;

    public Doctor(String name, BlockingQueue<LabResult> resultQueue){
        this.name=name;
        this.resultQueue=resultQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                LabResult result = resultQueue.take();
                if (result.isNoOrder()) {
                    System.out.println("Dr. " + name + " finished for the day");
                    break;
                }
                PatientRecord record = result.getRecord();
                record.addTestResult(result.getTestName() + ": " + result.getOutcome());
                record.addNote(name, "reviewed " + result.getTestName());
                System.out.println("Dr. " + name + " reviewed " + result.getOutcome()
                        + " result for " + record.getPatient().getName());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
    
    
// this is for producer in the producer-consumer pattern, where doctor produces lab request to the lab tech.