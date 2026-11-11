public class Main{
    public static void main(String[] args) {
        MedicineInventory inventory = new MedicineInventory();
        inventory.addStock("Paracetamol", 50);

        Patient p = new Patient(1, "Caren", "ear inflamation and tonsils");
        System.out.println("new patient:" + p);

        System.out.println("Dispense 20: " + inventory.dispenseMedicine("Paracetamol", 20));
        System.out.println("Stock now: " + inventory.getStock("Paracetamol"));
        System.out.println("Dispense 40: " + inventory.dispenseMedicine("Paracetamol", 40));
        System.out.println("Stock now: " + inventory.getStock("Paracetamol"));
    }
}