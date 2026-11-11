public class LabRequest{
    private final Patient patient;
    private final String testName;
    
    public LabRequest(Patient patient, String testName){
        this.patient = patient;
        this.testName = testName;
    }
    //using getter methods to get the encapsulated data from the class
    public Patient getPatient(){return patient;};
    public String getTestName(){return testName;};

    @Override 
    public String toString(){
        return testName + " for " + patient.getName();
    }
}