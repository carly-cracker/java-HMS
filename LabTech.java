//a Runnable that consumes until it sees the No_Order variable that acts as a stop sign to it
import java.util.concurrent.BlockingQueue;
import  java.util.concurrent.Semaphore; // semaphore holds a number of permits

public class LabTech implements Runnable { 
    private final String name;
    private final BlockingQueue<LabRequest> labQueue;
    private final Semaphore labMachines;

    //constructor for initialization
    public LabTech (String name, BlockingQueue<LabRequest> labQueue, Semaphore labMachines){
        this.name = name;
        this.labQueue = labQueue;
        this.labMachines = labMachines;
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
                labMachines.acquire();
                try{
                    System.out.println("Lab tech " + name + " is using the machine for " + request);
                    Thread.sleep(500);
                    System.out.println("Lab tech " + name + " finished processing " + request);
                } finally{
                    labMachines.release();
                }
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
