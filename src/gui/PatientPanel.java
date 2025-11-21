package gui;

import model.Patient;
import repository.HospitalService;
import util.IdGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PatientPanel extends JPanel {
    private final HospitalService service;
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);

    public PatientPanel(HospitalService service) {
        this.service = service;
        setLayout(new BorderLayout(8,8));

        JPanel form = new JPanel(new GridLayout(4,2,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Register Patient"));

        JTextField nameField = new JTextField();
        JTextField diseaseField = new JTextField();
        JTextField ageField = new JTextField();

        JButton addBtn = new JButton("Add Patient");
        addBtn.addActionListener(e -> {
            try {
                String pid = IdGenerator.nextPatientId();
                String name = nameField.getText().trim();
                String disease = diseaseField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                if (name.isEmpty()) { JOptionPane.showMessageDialog(this,"Name required."); return; }
                Patient p = new Patient(pid, name, disease, age);
                boolean ok = service.insertPatient(p);
                if (ok) {
                    refreshList();
                    nameField.setText(""); diseaseField.setText(""); ageField.setText("");
                    JOptionPane.showMessageDialog(this,"Patient added: "+pid);
                } else JOptionPane.showMessageDialog(this,"Add failed.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,"Age must be a number.");
            }
        });

        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Disease:")); form.add(diseaseField);
        form.add(new JLabel("Age:")); form.add(ageField);
        form.add(new JLabel()); form.add(addBtn);

        add(form, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createTitledBorder("Patients"));
        list.setVisibleRowCount(12);
        center.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> refreshList());
        JButton view = new JButton("View Selected Tests");
        view.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx >= 0) {
                String s = model.get(idx);
                String pid = parsePid(s);
                service.showAllTestsOfPatient(pid); // prints to console
                // show in dialog:
                Patient p = service.getPatient(pid);
                if (p != null) {
                    StringBuilder b = new StringBuilder();
                    p.getTests().forEach(t -> b.append(t.toString()).append("\n"));
                    JOptionPane.showMessageDialog(this, b.length()==0 ? "No tests" : b.toString());
                }
            }
        });
        actions.add(refresh); actions.add(view);
        center.add(actions, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        refreshList();
    }

    private String parsePid(String s) {
        int idx = s.indexOf("pid=");
        if (idx >= 0) {
            int comma = s.indexOf(',', idx);
            if (comma > idx) return s.substring(idx+4, comma);
        }
        return s.split("\\s+")[0];
    }

    private void refreshList() {
        model.clear();
        List<Patient> patients = service.getAllPatients();
        for (Patient p : patients) model.addElement(p.toString());
    }
}
