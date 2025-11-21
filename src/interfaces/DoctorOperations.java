package interfaces;

import model.Doctor;

import java.util.List;

public interface DoctorOperations {
    boolean insertDoctor(Doctor d);
    boolean removeDoctor(String doctorId);
    Doctor getDoctor(String id);
    List<Doctor> getAllDoctors();
}