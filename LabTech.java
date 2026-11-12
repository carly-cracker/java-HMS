//a Runnable that consumes until it sees the No_Order variable that acts as a stop sign to it
import java.util.concurrent.BlockingQueue;
import  java.util.concurrent.Semaphore; // semaphore holds a number of permits

public class LabTech implements Runnable { 
    private final String name;
    private final BlockingQueue<LabRequest> labQueue;
    private final BlockingQueue<LabResult> resultQueue; 
    private final Semaphore labMachines;

    //constructor for initialization
    public LabTech (String name, BlockingQueue<LabRequest> labQueue, Semaphore labMachines, BlockingQueue<LabResult> resultQueue){
        this.name = name;
        this.labQueue = labQueue;
        this.labMachines = labMachines;
        this.resultQueue = resultQueue;
    }

    // public void process(LabRequest request){
    //     System.out.println("Labtech" + name + "processed" + request );
    // }

    @Override 
    public void run() {
        try{
            while (true){
                // saves CPU time instead of busy waiting if the labqueue has nothing 
                LabRequest request = labQueue.take();
                if (request.isNoOrder()){
                    System.out.println("Lab tech " + name + " received stop signal. Exiting.");
                    break; //exit the loop cleanly
                }
                String outcome;
                labMachines.acquire();
                try {
                    Thread.sleep(50);   // the machine is busy
                    outcome = (request.getRecord().getPatient().getId() % 5 == 0) ? "POSITIVE" : "NEGATIVE";
                } finally {
                    labMachines.release();
                }
                // post the result AFTER releasing the machine, so a full result queue never holds a machine
                resultQueue.put(new LabResult(request.getRecord(), request.getTestName(), outcome));
                System.out.println("Lab tech " + name + " completed " + request + ": " + outcome);
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
