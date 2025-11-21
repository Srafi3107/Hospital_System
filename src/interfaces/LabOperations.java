package interfaces;

import model.Test;


public interface LabOperations {
    boolean addTestToPatient(String patientId, Test t);
    void showAllTestsOfPatient(String patientId);
}