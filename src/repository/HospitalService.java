package repository;

import interfaces.*;
import model.*;
import repository.DataRepository;
import util.IdGenerator;
import util.Logger;

import java.util.*;


public class HospitalService implements DoctorOperations, PatientOperations, LabOperations {
    private Map<String, Doctor> doctorMap = new LinkedHashMap<>();
    private Map<String, Patient> patientMap = new LinkedHashMap<>();
    private DataRepository repo;

    public HospitalService() {
        repo = new DataRepository();
        loadFromRepo();
    }

    private void loadFromRepo() {
        for (Doctor d : repo.loadAllDoctors()) doctorMap.put(d.getId(), d);
        for (Patient p : repo.loadAllPatients()) {
            List<Test> tests = repo.loadTestsForPatient(p.getPid());
            for (Test t : tests) p.addTest(t);
            patientMap.put(p.getPid(), p);
        }
        Logger.log("Loaded doctors=" + doctorMap.size() + " patients=" + patientMap.size());
    }

    // DoctorOperations
    @Override
    public boolean insertDoctor(Doctor d) {
        if (d == null || d.getId() == null) return false;
        if (doctorMap.containsKey(d.getId())) return false;
        doctorMap.put(d.getId(), d);
        boolean ok = repo.saveDoctor(d);
        Logger.log("Doctor inserted: " + d);
        return ok;
    }

    @Override
    public boolean removeDoctor(String doctorId) {
        if (!doctorMap.containsKey(doctorId)) return false;
        doctorMap.remove(doctorId);
        // Note: CSV repository is append-only for simplicity. A real project should rewrite file or use DB.
        Logger.log("Doctor removed: " + doctorId);
        return true;
    }

    @Override
    public Doctor getDoctor(String id) {
        return doctorMap.get(id);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctorMap.values());
    }

    // PatientOperations
    @Override
    public boolean insertPatient(Patient p) {
        if (p == null || p.getPid() == null) return false;
        if (patientMap.containsKey(p.getPid())) return false;
        patientMap.put(p.getPid(), p);
        boolean ok = repo.savePatient(p);
        Logger.log("Patient inserted: " + p);
        return ok;
    }

    @Override
    public boolean removePatient(String pid) {
        if (!patientMap.containsKey(pid)) return false;
        patientMap.remove(pid);
        Logger.log("Patient removed: " + pid);
        return true;
    }

    @Override
    public Patient getPatient(String pid) {
        return patientMap.get(pid);
    }

    @Override
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    // LabOperations
    @Override
    public boolean addTestToPatient(String patientId, Test t) {
        Patient p = getPatient(patientId);
        if (p == null) return false;
        if (t.getTestId() == null || t.getTestId().isEmpty()) t.setTestId(IdGenerator.nextId("T"));
        p.addTest(t);
        boolean ok = repo.saveTestForPatient(patientId, t);
        Logger.log("Added test to patient " + patientId + ": " + t);
        return ok;
    }

    @Override
    public void showAllTestsOfPatient(String patientId) {
        Patient p = getPatient(patientId);
        if (p == null) {
            System.out.println("Patient not found");
            return;
        }
        System.out.println("Tests for " + p.getName() + " (" + p.getPid() + "):");
        for (Test t : p.getTests()) System.out.println(" - " + t);
    }
}