public class LabRequest{
    public static final LabRequest NO_ORDER = new LabRequest(null, "STOP");
    private final Patient patient;
    private final String testName;
    
    public LabRequest(Patient patient, String testName){
        this.patient = patient;
        this.testName = testName;
    }
    //using getter methods to get the encapsulated data from the class
    public Patient getPatient(){return patient;};
    public String getTestName(){return testName;};
    public boolean isNoOrder(){
        return this == NO_ORDER;
    }

    @Override 
    public String toString(){
        if (patient == null) return "STOP signal";
        return testName + " for " + patient.getName();
    }
}