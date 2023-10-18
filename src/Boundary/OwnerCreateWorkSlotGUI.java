package Boundary;

import javax.swing.*;

// Need to extend JFrame?
public class OwnerCreateWorkSlotGUI {
    private JButton backButton;
    private JButton createButton;
    private JTextField dateField;
    private JTextField timeField;
    private JComboBox<String> roleField;
    private JTextField amountField;

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

        //Role Label
        JLabel roleLabel = new JLabel("Role: ");
        roleLabel.setBounds(75, 155, 100, 25);
        panel.add(roleLabel);

        //Role Field
        String[] roles = {"Chef", "Waiter", "Cashier"};
        roleField = new JComboBox<>(roles);
        roleField.setBounds(150, 155, 100, 25);
        panel.add(roleField);

        // Amount Label
        JLabel amountLabel = new JLabel("Amount: ");
        amountLabel.setBounds(75, 195, 100, 25);
        panel.add(amountLabel);

        // Amount Field
        amountField = new JTextField(20);
        amountField.setBounds(150, 195, 100, 25);
        panel.add(amountField);

        // Create Button
        createButton = new JButton("Create");
        createButton.setBounds(250, 220, 100, 25);
        panel.add(createButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(20, 220, 100, 25);
        panel.add(backButton);

        // Action for create button


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
