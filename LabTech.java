//a Runnable that consumes until it sees the No_Order variable that acts as a stop sign to it
import java.util.concurrent.BlockingQueue;

public class LabTech implements Runnable { 
    private final String name;
    private final BlockingQueue<LabRequest> labQueue;

    public LabTech (String name, BlockingQueue<LabRequest> labQueue){
        this.name = name;
        this.labQueue = labQueue;
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

                System.out.println("Lab tech " + name + " processing " + request);

                Thread.sleep(500); // Simulate time taken to process the lab request

            }
            
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
