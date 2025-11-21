package gui;

import model.*;
import repository.HospitalService;
import util.IdGenerator;

import javax.swing.*;
import java.awt.*;

public class TestPanel extends JPanel {
    private final HospitalService service;
    private final JTextArea output = new JTextArea();

    public TestPanel(HospitalService service) {
        this.service = service;
        setLayout(new BorderLayout(8,8));

        JPanel form = new JPanel(new GridLayout(5,2,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Assign Test to Patient"));

        JTextField pidField = new JTextField();
        JTextField tnameField = new JTextField();
        JTextField costField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Lab","Sample"});

        JButton addBtn = new JButton("Add Test");
        addBtn.addActionListener(e -> {
            String pid = pidField.getText().trim();
            String tname = tnameField.getText().trim();
            String costS = costField.getText().trim();
            String type = (String) typeBox.getSelectedItem();
            if (pid.isEmpty() || tname.isEmpty() || costS.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required.");
                return;
            }
            try {
                double cost = Double.parseDouble(costS);
                Test t = "Lab".equals(type)
                        ? new LabTest(IdGenerator.nextTestId(), tname, cost)
                        : new SampleTest(IdGenerator.nextTestId(), tname, cost);
                boolean ok = service.addTestToPatient(pid, t);
                if (ok) {
                    output.append(String.format("Added %s to %s\n", t, pid));
                } else {
                    JOptionPane.showMessageDialog(this, "Patient not found or error.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cost must be a number.");
            }
        });

        form.add(new JLabel("Patient ID:")); form.add(pidField);
        form.add(new JLabel("Test Name:")); form.add(tnameField);
        form.add(new JLabel("Cost:")); form.add(costField);
        form.add(new JLabel("Type:")); form.add(typeBox);
        form.add(new JLabel()); form.add(addBtn);

        add(form, BorderLayout.NORTH);

        output.setEditable(false);
        add(new JScrollPane(output), BorderLayout.CENTER);
    }
}
