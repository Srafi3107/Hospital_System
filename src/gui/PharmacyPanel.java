package gui;

import repository.HospitalService;

import javax.swing.*;
import java.awt.*;

public class PharmacyPanel extends JPanel {
    private final HospitalService service;
    private final JTextArea output = new JTextArea();

    public PharmacyPanel(HospitalService service) {
        this.service = service;
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(3,2,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Pharmacy (Demo)"));

        JTextField medField = new JTextField();
        JTextField qtyField = new JTextField();
        JButton addBtn = new JButton("Add Stock (Demo)");

        addBtn.addActionListener(e -> {
            String m = medField.getText().trim();
            String q = qtyField.getText().trim();
            if (m.isEmpty() || q.isEmpty()) { JOptionPane.showMessageDialog(this,"Enter medicine and qty"); return; }
            output.append(String.format("Stock added (demo): %s x %s\n", m, q));
        });

        form.add(new JLabel("Medicine:")); form.add(medField);
        form.add(new JLabel("Quantity:")); form.add(qtyField);
        form.add(new JLabel()); form.add(addBtn);

        add(form, BorderLayout.NORTH);
        output.setEditable(false);
        add(new JScrollPane(output), BorderLayout.CENTER);
    }
}
