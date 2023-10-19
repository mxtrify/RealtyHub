package Boundary;

import Entity.UserAccount;

import javax.swing.*;

public class CafeOwnerGUI {
    // Constructor
    public CafeOwnerGUI(UserAccount u) {
        displayCafeOwnerGUI(u);
    }

    // Display cafe owner GUI
    public void displayCafeOwnerGUI(UserAccount u) {
        JFrame frame = new JFrame("Cafe Owner");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome, Cafe Owner");
        titleLabel.setBounds(100,20, 500, 25);
        panel.add(titleLabel);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(150, 175, 100, 25);
        panel.add(logoutButton);

        // Create workSlot button
        JButton createWorkSlotButton = new JButton("Create Slot");
        createWorkSlotButton.setBounds(175, 100, 100, 25);
        panel.add(createWorkSlotButton);


        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for createWorkSlotButton
        createWorkSlotButton.addActionListener(e -> {
            frame.dispose();
            new OwnerCreateWorkSlotGUI(u);
        });

        // Action for logout button
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });
    }
}
