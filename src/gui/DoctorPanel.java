package gui;

import model.Doctor;
import repository.HospitalService;
import util.IdGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DoctorPanel extends JPanel {
    private final HospitalService service;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> doctorList = new JList<>(listModel);

    public DoctorPanel(HospitalService service) {
        this.service = service;
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(4,2,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Add New Doctor"));

        JTextField nameField = new JTextField();
        JTextField specField = new JTextField();
        JTextField roomField = new JTextField();

        JButton addBtn = new JButton("Add Doctor");
        addBtn.addActionListener(e -> {
            try {
                String id = IdGenerator.nextDoctorId();
                String name = nameField.getText().trim();
                String spec = specField.getText().trim();
                int room = Integer.parseInt(roomField.getText().trim());
                if (name.isEmpty() || spec.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name and Specialty are required.");
                    return;
                }
                Doctor d = new Doctor(id, name, spec, room);
                boolean ok = service.insertDoctor(d);
                if (ok) {
                    refreshList();
                    nameField.setText(""); specField.setText(""); roomField.setText("");
                    JOptionPane.showMessageDialog(this, "Doctor added: " + id);
                } else {
                    JOptionPane.showMessageDialog(this, "Doctor ID already exists or error.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Room must be a number.");
            }
        });

        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Specialty:")); form.add(specField);
        form.add(new JLabel("Room No:")); form.add(roomField);
        form.add(new JLabel()); form.add(addBtn);

        add(form, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createTitledBorder("Doctors"));
        doctorList.setVisibleRowCount(12);
        center.add(new JScrollPane(doctorList), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> refreshList());
        JButton remove = new JButton("Remove Selected");
        remove.addActionListener(e -> {
            int idx = doctorList.getSelectedIndex();
            if (idx >= 0) {
                String selected = listModel.get(idx);
                String id = selected.split("\\s+")[0].replace("(", "").replace(")", "");
                // selected string format below - use service removeDoctor
                // our toString is Doctor[id=..., name=..., ...
                // We'll parse id between id= and comma
                String parsedId = parseIdFromDisplay(selected);
                boolean ok = service.removeDoctor(parsedId);
                if (ok) { refreshList(); JOptionPane.showMessageDialog(this,"Removed "+parsedId); }
                else JOptionPane.showMessageDialog(this,"Remove failed.");
            }
        });
        actions.add(refresh); actions.add(remove);
        center.add(actions, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        refreshList();
    }

    private String parseIdFromDisplay(String s) {
        // try to find pattern id=XYZ,
        int idx = s.indexOf("id=");
        if (idx >= 0) {
            int comma = s.indexOf(',', idx);
            if (comma > idx) return s.substring(idx+3, comma);
        }
        return s.split("\\s+")[0];
    }

    private void refreshList() {
        listModel.clear();
        List<Doctor> doctors = service.getAllDoctors();
        for (Doctor d : doctors) {
            listModel.addElement(d.toString());
        }
    }
}
