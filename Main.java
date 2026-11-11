public class Main{
    public static void main(String[] args) {
        MedicineInventory inventory = new MedicineInventory();
        inventory.addStock("Paracetamol", 50);

        Patient patient = new Patient(1, "Caren", "ear inflamation and tonsils");
        Doctor doctor = new Doctor("Gift");
        Nurse nurse = new Nurse("Wanjiku");
        LabTech tech = new LabTech("Barry");
        Pharmacist pharmacist = new Pharmacist("Kip", inventory);

        nurse.recordVitals(patient);
        LabRequest request = doctor.requestLabTest(patient, "Malaria test");
        tech.process(request);
        pharmacist.dispenseMedicine(patient, "Paracetamol", 20);
        pharmacist.dispenseMedicine(patient, "Paracetamol", 40);
        System.out.println("Stock left: " + inventory.getStock("Paracetamol"));
    }
}