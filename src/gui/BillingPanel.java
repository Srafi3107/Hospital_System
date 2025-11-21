package gui;

import repository.HospitalService;

import javax.swing.*;
import java.awt.*;

public class BillingPanel extends JPanel {
    private final HospitalService service;
    private final JTextArea output = new JTextArea();

    public BillingPanel(HospitalService service) {
        this.service = service;
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(3,2,8,8));
        form.setBorder(BorderFactory.createTitledBorder("Billing (Demo)"));

        JTextField pidField = new JTextField();
        JButton genBtn = new JButton("Generate Bill (Demo)");

        genBtn.addActionListener(e -> {
            String pid = pidField.getText().trim();
            if (pid.isEmpty()) { JOptionPane.showMessageDialog(this,"Enter patient ID"); return; }
            // Demo: sum test costs
            var p = service.getPatient(pid);
            if (p==null) { JOptionPane.showMessageDialog(this,"Patient not found"); return; }
            double sum = p.getTests().stream().mapToDouble(t -> t.getCost()).sum();
            String bill = String.format("Bill for %s (%s)\nTests total: %.2f\n-- End --\n", p.getName(), pid, sum);
            output.append(bill);
        });

        form.add(new JLabel("Patient ID:")); form.add(pidField);
        form.add(new JLabel()); form.add(genBtn);

        add(form, BorderLayout.NORTH);
        output.setEditable(false);
        add(new JScrollPane(output), BorderLayout.CENTER);
    }
}
