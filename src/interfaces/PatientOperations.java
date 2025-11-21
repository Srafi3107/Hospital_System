package interfaces;

import model.Patient;

import java.util.List;

public interface PatientOperations {
    boolean insertPatient(Patient p);
    boolean removePatient(String pid);
    Patient getPatient(String pid);
    List<Patient> getAllPatients();
}
