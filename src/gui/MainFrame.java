package gui;

import repository.HospitalService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final HospitalService service;

    public MainFrame() {
        service = new HospitalService();

        setTitle("HealthHub - Hospital Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", new DashboardPanel(service));
        tabs.addTab("Doctors", new DoctorPanel(service));
        tabs.addTab("Patients", new PatientPanel(service));
        tabs.addTab("Tests", new TestPanel(service));
        tabs.addTab("Billing", new BillingPanel(service));
        tabs.addTab("Pharmacy", new PharmacyPanel(service));
        tabs.addTab("Lab", new LabPanel(service));

        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // optional: set look and feel to system
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
