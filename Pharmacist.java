import java.util.List;

public class Pharmacist implements Runnable{
    private final String name;
    private final MedicineInventory inventory;
    private final List<Patient> patients;

    public Pharmacist(String name ,MedicineInventory inventory ,List<Patient> patients){
        this.name = name;
        this.inventory = inventory;
        this.patients = patients;
    }

    public void dispenseMedicine(Patient patient, String medicine, int quantity){
        System.out.println("Pharmacist " + name + " is dispensing " + quantity + " of " + medicine + " to " + patient.getName());
        boolean success = inventory.dispenseMedicine(medicine, quantity);
        if(success){
            System.out.println("Dispensed successfully.");
        } else {
            System.out.println("Pharmacist " + name + ": not enough " + medicine + " for " + patient.getName());
        }
    }

    @Override 
    public void run(){
        try{
            for(Patient patient : patients){
                dispenseMedicine(patient, "Paracetamol", 5);
                Thread.sleep(100); // Simulate time taken to dispense medicine
            }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}