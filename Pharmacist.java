public class Pharmacist{
    private final String name;
    private final MedicineInventory inventory;

    public Pharmacist(String name ,MedicineInventory inventory ){
        this.name = name;
        this.inventory = inventory;
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
}