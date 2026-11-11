public class Doctor{
    private final String name;
    
    public Doctor(String name){
        this.name=name;
    }

    public LabRequest requestLabTest(Patient patient, String testName){
        System.out.println("Dr. " + name + " requests " + testName + " for " + patient.getName());
        return new LabRequest(patient, testName);
    }
    public String getName() { return name; }
}