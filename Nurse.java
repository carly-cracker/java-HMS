public class Nurse {
    private final String name;

    public Nurse(String name) { this.name = name; }

    public void recordVitals(Patient patient) {
        System.out.println("Nurse " + name + " recorded vitals for " + patient.getName());
    }
}