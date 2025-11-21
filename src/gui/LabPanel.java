package gui;

import repository.HospitalService;

import javax.swing.*;
import java.awt.*;

public class LabPanel extends JPanel {
    private final HospitalService service;
    private final JTextArea output = new JTextArea();

    public LabPanel(HospitalService service) {
        this.service = service;
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(3,2,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Lab (Demo)"));

        JTextField pidField = new JTextField();
        JButton notifyBtn = new JButton("Notify Patient (Demo)");

        notifyBtn.addActionListener(e -> {
            String pid = pidField.getText().trim();
            if (pid.isEmpty()) { JOptionPane.showMessageDialog(this,"Enter patient ID"); return; }
            // demo: pretend to notify
            output.append("Notification sent to patient " + pid + " (demo)\n");
        });

        form.add(new JLabel("Patient ID:")); form.add(pidField);
        form.add(new JLabel()); form.add(notifyBtn);

        add(form, BorderLayout.NORTH);
        output.setEditable(false);
        add(new JScrollPane(output), BorderLayout.CENTER);
    }
}
