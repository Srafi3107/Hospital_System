package repository;

import model.Doctor;
import model.Patient;
import model.Test;
import util.Logger;

import java.io.*;
import java.nio.file.*;
import java.util.*;

// CSV-based persistence. Each entity uses its own CSV file under data/.
public class DataRepository {
    private static final String DATA_DIR = "data";
    private static final String DOCTORS = DATA_DIR + File.separator + "doctors.csv";
    private static final String PATIENTS = DATA_DIR + File.separator + "patients.csv";
    private static final String TESTS = DATA_DIR + File.separator + "tests.csv"; // pid,testId,testName,type,cost

    public DataRepository() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            createIfMissing(DOCTORS, "id,name,specialty,room");
            createIfMissing(PATIENTS, "pid,name,disease,age");
            createIfMissing(TESTS, "pid,testId,testName,type,cost");
        } catch (IOException e) {
            Logger.log("Failed to initialize data directory: " + e.getMessage());
        }
    }

    private void createIfMissing(String path, String header) throws IOException {
        Path p = Paths.get(path);
        if (!Files.exists(p)) {
            Files.write(p, Collections.singleton(header), StandardOpenOption.CREATE);
        }
    }

    // Doctors
    public synchronized boolean saveDoctor(Doctor d) {
        if (d == null) return false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTORS, true))) {
            String line = String.join(",",
                    escape(d.getId()), escape(d.getName()), escape(d.getSpecialty()), String.valueOf(d.getRoomNo()));
            bw.write(line);
            bw.newLine();
            return true;
        } catch (IOException e) {
            Logger.log("Error writing doctor: " + e.getMessage());
            return false;
        }
    }

    public synchronized List<Doctor> loadAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORS))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = split(line);
                if (cols.length >= 4) {
                    Doctor d = new Doctor(cols[0], cols[1], cols[2], Integer.parseInt(cols[3]));
                    list.add(d);
                }
            }
        } catch (IOException e) {
            Logger.log("Error reading doctors: " + e.getMessage());
        }
        return list;
    }

    // Patients
    public synchronized boolean savePatient(Patient p) {
        if (p == null) return false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENTS, true))) {
            String line = String.join(",",
                    escape(p.getPid()), escape(p.getName()), escape(p.getDisease()), String.valueOf(p.getAge()));
            bw.write(line);
            bw.newLine();
            return true;
        } catch (IOException e) {
            Logger.log("Error writing patient: " + e.getMessage());
            return false;
        }
    }

    public synchronized List<Patient> loadAllPatients() {
        List<Patient> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENTS))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = split(line);
                if (cols.length >= 4) {
                    Patient p = new Patient(cols[0], cols[1], cols[2], Integer.parseInt(cols[3]));
                    list.add(p);
                }
            }
        } catch (IOException e) {
            Logger.log("Error reading patients: " + e.getMessage());
        }
        return list;
    }

    // Tests
    public synchronized boolean saveTestForPatient(String pid, Test t) {
        if (pid == null || t == null) return false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TESTS, true))) {
            String line = String.join(",",
                    escape(pid), escape(t.getTestId()), escape(t.getTestName()), escape(t.getType()), String.valueOf(t.getCost()));
            bw.write(line);
            bw.newLine();
            return true;
        } catch (IOException e) {
            Logger.log("Error writing test: " + e.getMessage());
            return false;
        }
    }

    public synchronized List<Test> loadTestsForPatient(String pid) {
        List<Test> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TESTS))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = split(line);
                if (cols.length >= 5 && cols[0].equals(pid)) {
                    String testId = cols[1];
                    String testName = cols[2];
                    String type = cols[3];
                    double cost = Double.parseDouble(cols[4]);
                    Test t = "Lab".equalsIgnoreCase(type) ? new model.LabTest(testId, testName, cost)
                            : new model.SampleTest(testId, testName, cost);
                    list.add(t);
                }
            }
        } catch (IOException e) {
            Logger.log("Error reading tests: " + e.getMessage());
        }
        return list;
    }

    // Helpers
    private String escape(String s) { return s == null ? "" : s.replace("\n", " ").replace(",", ";"); }
    private String[] split(String line) { return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); }
}