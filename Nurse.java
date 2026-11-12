import java.util.List;

public class Nurse implements Runnable { //MAKING NURSE RUNNABLE SO THAT IT CAN BE RUN IN ITS OWN THREAD
    private final String name;
    private final List<Patient> patients;

    public Nurse(String name, List<Patient> patients) { 
        this.name = name;
        this.patients = patients;
    }

    // public void recordVitals(Patient patient) {
    //     System.out.println("Nurse " + name + " recorded vitals for " + patient.getName());
    // }
    @Override 
    public void run(){
        try{
            for(Patient patient : patients){
                System.out.println("Nurse " + name + " recorded vitals for " + patient.getName());
                Thread.sleep(100); // Simulate time taken to record vitals
            }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}