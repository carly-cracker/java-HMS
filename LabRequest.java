public class LabRequest{

    public static final LabRequest NO_ORDER = new LabRequest(null, "STOP");
    private final PatientRecord record;
    private final String testName;
    
    public LabRequest(PatientRecord record, String testName){
        this.record = record;
        this.testName = testName;
    }
    //using getter methods to get the encapsulated data from the class
    public PatientRecord getRecord(){return record;};
    public String getTestName(){return testName;};
    public boolean isNoOrder(){
        return this == NO_ORDER;
    }

    @Override 
    public String toString(){
        if (record == null) return "STOP signal";
        return testName + " for " + record.getPatient().getName();
    }
}