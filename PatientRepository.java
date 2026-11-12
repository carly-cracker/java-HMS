import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class PatientRepository {
    private final ConcurrentHashMap<Integer, PatientRecord> records = new ConcurrentHashMap<>();

    public void add(Patient patient) {
        records.put(patient.getId(), new PatientRecord(patient));
    }

    public PatientRecord get(int id) {
        return records.get(id);
    }

    public int size() {
        return records.size();
    }

    public Collection<PatientRecord> all() {
        return records.values();
    }
}