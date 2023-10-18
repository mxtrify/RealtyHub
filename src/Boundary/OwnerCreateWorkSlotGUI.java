package Boundary;

import javax.swing.*;

// Need to extend JFrame?
public class OwnerCreateWorkSlotGUI {
    private JButton backButton;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField amount;

    public OwnerCreateWorkSlotGUI(){
        displayCreateWorkSlotGUI();
    }

    public void displayCreateWorkSlotGUI(){
        JFrame frame = new JFrame("Create Work Slot");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Create Work Slot");
        titleLabel.setBounds(100, 20, 500, 25);
        panel.add(titleLabel);

        // Date Label
        JLabel dateLabel = new JLabel("Date: ");
        dateLabel.setBounds(75, 75, 100, 25);
        panel.add(dateLabel);

        // Date Field
        dateField = new JTextField(20);
        dateField.setBounds(150, 75, 175, 25);
        panel.add(dateField);

        // Time Label
        JLabel timeLabel = new JLabel("Time: ");
        timeLabel.setBounds(75, 115, 100, 25);
        panel.add(timeLabel);

        // Time Field
        timeField = new JTextField(20);
        timeField.setBounds(150, 115, 100, 25);
        panel.add(timeField);


        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(20, 220, 100, 25);
        panel.add(backButton);

        // Action for back button
        backButton.addActionListener(e -> {
            frame.dispose();
            new CafeOwnerGUI();
        });

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
