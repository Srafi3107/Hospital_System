package app;


import model.*;
import repository.HospitalService;
import util.IdGenerator;
import util.Logger;

import java.util.List;
import java.util.Scanner;

public class Start {
    private static final Scanner sc = new Scanner(System.in);
    private static final HospitalService service = new HospitalService();

    public static void main(String[] args) {
        Logger.log("Hospital Management System (Console) - Starting");
        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": doctorMenu(); break;
                case "2": patientMenu(); break;
                case "3": testMenu(); break;
                case "4": running = false; break;
                default: System.out.println("Invalid choice");
            }
        }
        Logger.log("Shutting down");
    }

    private static void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Doctor Management");
        System.out.println("2. Patient Management");
        System.out.println("3. Test Management");
        System.out.println("4. Exit");
        System.out.print("Choice: ");
    }

    private static void doctorMenu() {
        System.out.println("\n-- Doctor Menu --");
        System.out.println("1. Add Doctor");
        System.out.println("2. Get Doctor by ID");
        System.out.println("3. Show All Doctors");
        System.out.println("4. Back");
        System.out.print("Option: ");
        String opt = sc.nextLine().trim();
        switch (opt) {
            case "1": addDoctor(); break;
            case "2": getDoctor(); break;
            case "3": showAllDoctors(); break;
            default: break;
        }
    }

    private static void addDoctor() {
        String id = IdGenerator.nextDoctorId();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Specialty: ");
        String spec = sc.nextLine().trim();
        System.out.print("Room No: ");
        int room = Integer.parseInt(sc.nextLine().trim());
        Doctor d = new Doctor(id, name, spec, room);
        service.insertDoctor(d);
        System.out.println("Inserted doctor with ID: " + id);
    }

    private static void getDoctor() {
        System.out.print("Enter Doctor ID: ");
        String id = sc.nextLine().trim();
        Doctor d = service.getDoctor(id);
        if (d == null) System.out.println("Doctor not found"); else System.out.println(d);
    }

    private static void showAllDoctors() {
        List<Doctor> doctors = service.getAllDoctors();
        if (doctors.isEmpty()) System.out.println("No doctors available");
        else doctors.forEach(System.out::println);
    }

    private static void patientMenu() {
        System.out.println("\n-- Patient Menu --");
        System.out.println("1. Add Patient");
        System.out.println("2. Get Patient by ID");
        System.out.println("3. Show All Patients");
        System.out.println("4. Back");
        System.out.print("Option: ");
        String opt = sc.nextLine().trim();
        switch (opt) {
            case "1": addPatient(); break;
            case "2": getPatient(); break;
            case "3": showAllPatients(); break;
            default: break;
        }
    }

    private static void addPatient() {
        String pid = IdGenerator.nextPatientId();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Disease: ");
        String disease = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine().trim());
        Patient p = new Patient(pid, name, disease, age);
        service.insertPatient(p);
        System.out.println("Inserted patient with ID: " + pid);
    }

    private static void getPatient() {
        System.out.print("Enter Patient ID: ");
        String pid = sc.nextLine().trim();
        Patient p = service.getPatient(pid);
        if (p == null) System.out.println("Patient not found"); else System.out.println(p);
    }

    private static void showAllPatients() {
        List<Patient> patients = service.getAllPatients();
        if (patients.isEmpty()) System.out.println("No patients available");
        else patients.forEach(System.out::println);
    }

    private static void testMenu() {
        System.out.println("\n-- Test Menu --");
        System.out.println("1. Add Test to Patient");
        System.out.println("2. Show Tests of Patient");
        System.out.println("3. Back");
        System.out.print("Option: ");
        String opt = sc.nextLine().trim();
        switch (opt) {
            case "1": addTestToPatient(); break;
            case "2": showTestsOfPatient(); break;
            default: break;
        }
    }

    private static void addTestToPatient() {
        System.out.print("Enter Patient ID: ");
        String pid = sc.nextLine().trim();
        Patient p = service.getPatient(pid);
        if (p == null) { System.out.println("Patient not found"); return; }
        System.out.print("Test Name: ");
        String tname = sc.nextLine().trim();
        System.out.print("Test Cost: ");
        double cost = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Type (1=Lab,2=Sample): ");
        String type = sc.nextLine().trim();
        Test t = "1".equals(type) ? new LabTest(IdGenerator.nextTestId(), tname, cost)
                : new SampleTest(IdGenerator.nextTestId(), tname, cost);
        service.addTestToPatient(pid, t);
        System.out.println("Test added: " + t);
    }

    private static void showTestsOfPatient() {
        System.out.print("Enter Patient ID: ");
        String pid = sc.nextLine().trim();
        service.showAllTestsOfPatient(pid);
    }
}