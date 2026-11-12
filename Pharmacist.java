public class Pharmacist implements Runnable{
    private final String name;
    private final MedicineInventory inventory;
    private final int fromId;
    private final int toId;
    private final PatientCounter counter;
    private final PatientRepository repository;

    public Pharmacist(String name ,MedicineInventory inventory ,int fromId, int toId, PatientCounter counter, PatientRepository repository){
        this.name = name;
        this.inventory = inventory;
        this.fromId = fromId;
        this.toId = toId;
        this.counter = counter;
        this.repository = repository;
    }

    public void dispenseMedicine(Patient patient, String medicine, int quantity){
       
        
        if(inventory.dispenseMedicine(medicine, quantity)){
            counter.recordTreated();
            System.out.println("Pharmacist " + name + " gave " + quantity + " " + medicine
                + " to " + patient.getName() + " - dispensed successfully");
        } else {
            System.out.println("Pharmacist " + name + ": not enough " + medicine + " for " + patient.getName());
        }
    }

    @Override 
    public void run(){
        try {
            for (int id = fromId; id <= toId; id++) {
                String patientName = repository.get(id).getPatient().getName();
                if (inventory.dispenseMedicine("Paracetamol", 1)) {
                    counter.recordTreated();
                    System.out.println("Pharmacist " + name + " gave 1 dose to " + patientName);
                } else {
                    System.out.println("Pharmacist " + name + ": no stock left for " + patientName);
                }
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}