public class LabResult {
    public static final LabResult NO_ORDER = new LabResult(null, "STOP", "STOP");

    private final PatientRecord record;
    private final String testName;
    private final String outcome;

    public LabResult(PatientRecord record, String testName, String outcome) {
        this.record = record;
        this.testName = testName;
        this.outcome = outcome;
    }

    public PatientRecord getRecord() { return record; }
    public String getTestName() { return testName; }
    public String getOutcome() { return outcome; }
    public boolean isNoOrder() { return this == NO_ORDER; }
}