package gui;

import repository.HospitalService;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final HospitalService service;
    private final JLabel doctorsLabel = new JLabel();
    private final JLabel patientsLabel = new JLabel();
    private final JLabel testsLabel = new JLabel();

    public DashboardPanel(HospitalService service) {
        this.service = service;
        setLayout(new BorderLayout(10,10));
        JPanel top = new JPanel(new GridLayout(1,3,10,10));
        top.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        top.add(card("Total Doctors", doctorsLabel));
        top.add(card("Total Patients", patientsLabel));
        top.add(card("Total Tests (records)", testsLabel));
        add(top, BorderLayout.NORTH);

        refreshStats();
    }

    private JPanel card(String title, JLabel valueLabel) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(title));
        valueLabel.setFont(valueLabel.getFont().deriveFont(24f));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(valueLabel, BorderLayout.CENTER);
        return p;
    }

    public void refreshStats() {
        doctorsLabel.setText(String.valueOf(service.getAllDoctors().size()));
        patientsLabel.setText(String.valueOf(service.getAllPatients().size()));
        // count tests by summing patient test lists
        int tests = service.getAllPatients().stream().mapToInt(p -> p.getTests().size()).sum();
        testsLabel.setText(String.valueOf(tests));
    }
}
